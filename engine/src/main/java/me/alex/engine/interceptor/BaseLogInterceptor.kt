package me.alex.engine.interceptor

import me.alex.engine.Engine
import me.alex.engine.log.httpLog.HttpLog
import me.alex.engine.log.httpLog.HttpLog.printFileResponse
import me.alex.engine.log.httpLog.HttpLog.printJsonResponse
import me.alex.engine.log.LogUtil
import me.alex.engine.log.utils.CharacterHandler
import me.alex.engine.log.utils.UrlEncoderUtils
import me.alex.engine.log.utils.ZipHelper
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
class BaseLogInterceptor(debug: Boolean) : Interceptor {

    private val isDebug = debug //是否调试模式

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (isDebug) {//调试模式下 打印信息
            //计算唯一id
            val tag = Engine.httpLogTag + " - " + LogUtil.getGenId()
            //打印请求信息
            if (request.body != null && LogUtil.isParseAble(request.body!!.contentType())) {
                HttpLog.printJsonRequest(tag, request, parseParams(request)!!)
            } else {
                HttpLog.printFileRequest(tag, request)
            }


            //打印响应信息
            val t1: Long = System.nanoTime()
            lateinit var response: Response
            try {
                response = chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val t2: Long = System.nanoTime()
            val responseBody: ResponseBody? = response.body
            var bodyString: String? = ""
            val segmentList = request.url.encodedPathSegments
            val header: String = response.headers.toString()
            val code: Int = response.code
            val isSuccessful: Boolean = response.isSuccessful
            val message: String = response.message
            val url: String = response.request.url.toString()

            if (responseBody != null && LogUtil.isParseAble(responseBody.contentType())) {
                bodyString = printResult(request, response, true)
                //打印json返回结果
                printJsonResponse(
                    tag,
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                    isSuccessful,
                    code,
                    header,
                    responseBody.contentType(),
                    bodyString,
                    segmentList,
                    message,
                    url
                )
            } else {
                printFileResponse(
                    tag,
                    TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                    isSuccessful,
                    code,
                    header,
                    segmentList,
                    message,
                    url
                )
            }

            return chain.proceed(request)
        } else {
            //发布模式下 直接返回
            return chain.proceed(request)
        }

    }


    /**
     * 解析请求服务器的请求参数
     *
     * @param request [Request]
     * @return 解析后的请求信息
     */
    private fun parseParams(request: Request): String? {
        return try {
            val body = request.newBuilder().build().body ?: return ""
            val requestBuffer = Buffer()
            body.writeTo(requestBuffer)
            var charset = Charset.forName("UTF-8")
            val contentType = body.contentType()
            if (contentType != null) {
                charset = contentType.charset(charset)
            }
            var json: String? = requestBuffer.readString(charset!!)
            if (UrlEncoderUtils.hasUrlEncoded(json!!)) {
                json = URLDecoder.decode(json, convertCharset(charset))
            }
            CharacterHandler.jsonFormat(json!!)
        } catch (e: IOException) {
            e.printStackTrace()
            return "{\"error\": \"" + e.message + "\"}"
        }
    }

    private fun convertCharset(charset: Charset): String {
        val s = charset.toString()
        val i = s.indexOf("[")
        return if (i == -1) {
            s
        } else s.substring(i + 1, s.length - 1)
    }

    /**
     * 打印响应结果
     *
     * @param request     [Request]
     * @param response    [Response]
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
        return try {
            //读取服务器返回的结果
            val responseBody = response.newBuilder().build().body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            //获取content的压缩类型
            val encoding = response
                .headers["Content-Encoding"]
            val clone = buffer.clone()

            //解析response content
            parseContent(responseBody, encoding, clone)
        } catch (e: IOException) {
            e.printStackTrace()
            "{\"error\": \"" + e.message + "\"}"
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody [ResponseBody]
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private fun parseContent(
        responseBody: ResponseBody,
        encoding: String?,
        clone: Buffer
    ): String? {
        var charset = Charset.forName("UTF-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        //content 使用 gzip 压缩
        return when {
            "gzip".equals(encoding, ignoreCase = true) -> {
                //解压
                ZipHelper.decompressForGzip(
                    clone.readByteArray(),
                    convertCharset(charset)
                )
            }
            "zlib".equals(encoding, ignoreCase = true) -> {
                //content 使用 zlib 压缩
                ZipHelper.decompressToStringForZlib(
                    clone.readByteArray(),
                    convertCharset(charset)
                )
            }
            else -> {
                //content 没有被压缩, 或者使用其他未知压缩方式
                clone.readString(charset!!)
            }
        }
    }
}