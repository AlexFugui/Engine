package me.alex.engine.log

import android.util.Log
import okhttp3.MediaType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
object LogUtil {
    /**
     * 获取代码行号
     */
    @Synchronized
    fun getCodeLine(): String {
        val stackTraceElements = Thread.currentThread().stackTrace
        var stackStr = ""
        stackStr = ""
        if (stackTraceElements.size < 5) {
            stackStr += ""
        } else {
            stackTraceElements[4].let {
                stackStr += "[ ${it.methodName} ] (${it.fileName}:${it.lineNumber}) \n"
            }
//            for (stackTraceElement in stackTraceElements) {
//                Log.i("LogUtil", "stackTraceElement: ${stackTraceElement.methodName}")
//            }
        }
        return stackStr
    }

    /**
     * 获取基于时间戳Id
     */
    private val format: DateFormat = SimpleDateFormat("ddhhmmssSSS", Locale.CHINA)
    private val atomicLong: AtomicLong = AtomicLong()

    @Synchronized
    fun getGenId(): String {
        var currentTime: Long = format.format(Date()).toLong()
        var previousTime: Long = atomicLong.get()
        if (currentTime <= previousTime) {
            currentTime = ++previousTime
        }
        atomicLong.set(currentTime)
        return "${currentTime.toString(Character.MAX_RADIX).uppercase()} "
    }


    /**
     * 是否可以解析
     *
     * @param mediaType [MediaType]
     * @return `true` 为可以解析
     */
    fun isParseAble(mediaType: MediaType?): Boolean {
        return if (mediaType?.type == null) {
            false
        } else isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType)
    }

    fun isText(mediaType: MediaType?): Boolean {
        return if (mediaType == null) {
            false
        } else "text" == mediaType.type
    }

    fun isPlain(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("plain") ?: false
    }

    fun isJson(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("json") ?: false
    }

    fun isXml(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("xml") ?: false
    }

    fun isHtml(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("html") ?: false
    }

    fun isForm(mediaType: MediaType?): Boolean {
        return mediaType?.subtype?.lowercase(Locale.getDefault())?.contains("x-www-form-urlencoded")
            ?: false
    }
}