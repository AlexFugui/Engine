package me.alex.engine.interceptor

import android.util.Log
import com.drake.net.interceptor.LogRecordInterceptor
import me.alex.engine.log.LOG
import me.alex.engine.log.LogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
class BaseLogInterceptor(enabled: Boolean) : LogRecordInterceptor(enabled) {

    init {
        LogRecorder.enabled = enabled
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!enabled) {
            return chain.proceed(request)
        }

        val generateId = LogUtil.getGenId()
//        LOG.I("generateId", generateId)
//        LOG.I("url", request.url.toString())
//        LOG.I("method", request.method)
//        LOG.I("headers", request.headers.toMultimap())
//        LOG.I("requestString", requestString(request))
//        InterceptLog.logRequest(generateId, request.url.toString(), request.method, request.headers.toMultimap(), requestString(request))

        LogRecorder.recordRequest(
            generateId,
            request.url.toString(),
            request.method,
            request.headers.toMultimap(),
            requestString(request)
        )
        try {
            val response = chain.proceed(request)
            LogRecorder.recordResponse(
                generateId,
                System.currentTimeMillis(),
                response.code,
                response.headers.toMultimap(),
                responseString(response)
            )
            return response
        } catch (e: Exception) {
            LogRecorder.recordException(
                generateId,
                System.currentTimeMillis(),
                -1,
                null,
                Log.getStackTraceString(e)
            )
            throw e
        }
    }

    override fun requestString(request: Request): String {
        return super.requestString(request) ?: ""
    }

    override fun responseString(response: Response): String {
        return super.responseString(response) ?: ""
    }
}