package me.alex.engine.log.httpLog

import android.text.TextUtils.isEmpty
import me.alex.engine.Engine
import me.alex.engine.log.LOG
import me.alex.engine.log.LogUtil
import me.alex.engine.log.utils.CharacterHandler
import okhttp3.MediaType
import okhttp3.Request


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
object HttpLog {
    private val TAG = "ArmsHttpLog"
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private val DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR

    private val OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")
    private val OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")

    private val N = "\n"
    private val T = "\t"
    private val REQUEST_UP_LINE =
        "┌────── Request ────────────────────────────────────────────────────────────────────────"
    private val RESPONSE_UP_LINE =
        "┌────── Response ───────────────────────────────────────────────────────────────────────"
    private val END_LINE =
        "└───────────────────────────────────────────────────────────────────────────────────────"
    private val BODY_TAG = "Body:"
    private val URL_TAG = "URL: "
    private val METHOD_TAG = "Method: @"
    private val HEADERS_TAG = "Headers:"
    private val STATUS_CODE_TAG = "Status Code: "
    private val RECEIVED_TAG = "Received in: "
    private val CORNER_UP = "┌ "
    private val CORNER_BOTTOM = "└ "
    private val CENTER_LINE = "├ "
    private val DEFAULT_LINE = "│ "


    fun printJsonRequest(tag: String, request: Request, bodyString: String) {
        val requestBody: String =
            LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString

        LOG.httpLog(tag, REQUEST_UP_LINE)
        logLines(tag, arrayOf(URL_TAG + request.url), false)
        logLines(tag, getRequest(request), true)
        logLines(tag, requestBody.split(LINE_SEPARATOR).toTypedArray(), true)
        LOG.httpLog(tag, END_LINE)
    }

    fun printFileRequest(tag: String, request: Request) {
        LOG.httpLog(tag, REQUEST_UP_LINE)
        logLines(tag, arrayOf(URL_TAG + request.url), false)
        logLines(tag, getRequest(request), true)
        logLines(tag, OMITTED_REQUEST, true);
        LOG.httpLog(tag, END_LINE)
    }

    fun printJsonResponse(
        tag: String,
        chainMs: Long,
        isSuccessful: Boolean,
        code: Int,
        headers: String,
        contentType: MediaType?,
        bodyString: String?,
        segments: List<String>,
        message: String,
        responseUrl: String
    ) {
        val logBodyString = when {
            LogUtil.isJson(contentType) -> CharacterHandler.jsonFormat(
                bodyString!!
            )
            LogUtil.isXml(contentType) -> CharacterHandler.xmlFormat(bodyString)
            else -> bodyString
        }

        val responseBody: String =
            LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + logBodyString
        val urlLine = arrayOf(URL_TAG + responseUrl, N)

        LOG.httpLog(tag, RESPONSE_UP_LINE)
        logLines(tag, urlLine, true)
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true)
        logLines(tag, responseBody.split(LINE_SEPARATOR).toTypedArray(), true)
        LOG.httpLog(tag, END_LINE)
    }

    fun printFileResponse(
        tag: String,
        chainMs: Long,
        isSuccessful: Boolean,
        code: Int,
        headers: String,
        segments: List<String>,
        message: String,
        responseUrl: String
    ) {
        val urlLine = arrayOf<String>(URL_TAG + responseUrl, N)

        LOG.httpLog(tag, RESPONSE_UP_LINE)
        logLines(tag, urlLine, true)
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true)
        logLines(tag, OMITTED_RESPONSE, true)
        LOG.httpLog(tag, END_LINE)
    }


    /**
     * 对 `lines` 中的信息进行逐行打印
     *
     * @param tag
     * @param lines
     * @param withLineSize 为 `true` 时, 每行的信息长度不会超过120, 超过则自动换行
     */
    private fun logLines(tag: String, lines: Array<String>, withLineSize: Boolean) {
        for (line in lines) {
            val lineLength = line.length
            val maxLongSize = if (withLineSize) Engine.maxHttpLogSize else lineLength
            for (i in 0..lineLength / maxLongSize) {
                val start = i * maxLongSize
                var end = (i + 1) * maxLongSize
                end = if (end > line.length) line.length else end
                LOG.httpLog(tag, DEFAULT_LINE + line.substring(start, end))
            }
        }
    }

    private fun getRequest(request: Request): Array<String> {
        val log: String
        val header = request.headers.toString()
        log =
            METHOD_TAG + request.method + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else HEADERS_TAG + LINE_SEPARATOR + dotHeaders(
                        header
                    )
        return log.split(LINE_SEPARATOR as String).toTypedArray()
    }

    private fun slashSegments(segments: List<String>): String {
        val segmentString = StringBuilder()
        for (segment in segments) {
            segmentString.append("/").append(segment)
        }
        return segmentString.toString()
    }

    /**
     * 对 `header` 按规定的格式进行处理
     *
     * @param header
     * @return
     */
    private fun dotHeaders(header: String): String? {
        val headers: Array<String> =
            header.split(LINE_SEPARATOR as String).toTypedArray()
        val builder = java.lang.StringBuilder()
        var tag = "─ "
        if (headers.size > 1) {
            for (i in headers.indices) {
                tag = when (i) {
                    0 -> {
                        CORNER_UP
                    }
                    headers.size - 1 -> {
                        CORNER_BOTTOM
                    }
                    else -> {
                        CENTER_LINE
                    }
                }
                builder.append(tag).append(headers[i]).append("\n")
            }
        } else {
            for (item in headers) {
                builder.append(tag).append(item).append("\n")
            }
        }
        return builder.toString()
    }

    private fun getResponse(
        header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
        segments: List<String>, message: String
    ): Array<String> {
        val log: String
        val segmentString: String =
            slashSegments(segments)
        log = ((if (!isEmpty(segmentString)) "$segmentString - " else "") + "is success : "
                + isSuccessful + " - " + RECEIVED_TAG + tookMs + "ms" + DOUBLE_SEPARATOR + STATUS_CODE_TAG +
                code + " / " + message + DOUBLE_SEPARATOR + if (isEmpty(
                header
            )
        ) "" else HEADERS_TAG + LINE_SEPARATOR +
                dotHeaders(header))
        return log.split(LINE_SEPARATOR as String).toTypedArray()
    }


}