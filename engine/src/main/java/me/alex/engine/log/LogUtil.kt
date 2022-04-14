package me.alex.engine.log

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
        return " ${currentTime.toString(Character.MAX_RADIX).uppercase()} "
    }
}