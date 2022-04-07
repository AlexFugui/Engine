package me.alex.engine.log

import android.util.Log
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
class Logger {

    companion object {
        private const val TAG = "AlexEngine"
        private val format: DateFormat = SimpleDateFormat("ddhhmmssSSS", Locale.CHINA)
        private val previousTime = AtomicLong()

        fun d(msg: String) {
            Log.d(TAG, getCodeLine() + msg)
        }

        fun e(msg: String) {
            Log.e(TAG, getCodeLine() + msg)
        }

        /**
         * 获取代码行号
         */
        @Synchronized
        private fun getCodeLine(): String {
            val stackTraceElements = Thread.currentThread().stackTrace
            var stackStr = ""
            stackStr = ""
            if (stackTraceElements.size < 5) {
                stackStr += ""
            } else {
                stackTraceElements[4].let {
                    stackStr += "Log: [${it.methodName}] (${it.fileName}:${it.lineNumber}) \n"
                }
            }
            return stackStr
        }


        /**
         * 获取基于时间戳Id
         */
        @Synchronized
        private fun getGenId(): String {
            var currentTime: Long = format.format(Date()).toLong()
            var previousTime: Long = previousTime.get()
            if (currentTime <= previousTime) {
                currentTime = ++previousTime
            }
            Companion.previousTime.set(currentTime)
            return " ${currentTime.toString(Character.MAX_RADIX).uppercase()} "
        }

    }

}