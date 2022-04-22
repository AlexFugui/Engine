package me.alex.engine.log

import android.util.Log
import me.alex.engine.Engine
import me.alex.engine.log.LogUtil.getCodeLine


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
object LOG {

    private const val RESPONSE_UP_LINE =
        "┌───────────────────────────────────────────────────────────────────────────────────────"
    private const val CENTER_LINE = "├ "
    private const val END_LINE =
        "└───────────────────────────────────────────────────────────────────────────────────────"

    fun I(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.i(Engine.logTag, RESPONSE_UP_LINE)
            Log.i(Engine.logTag, CENTER_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.i(Engine.logTag, "$CENTER_LINE msg${i + 1} : " + msg[i].toString())
            }
            Log.i(Engine.logTag, END_LINE)
        }
    }

    fun E(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.e(Engine.logTag, RESPONSE_UP_LINE)
            Log.e(Engine.logTag, CENTER_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.e(Engine.logTag, "$CENTER_LINE msg${i + 1} : " + msg[i].toString())
            }
            Log.e(Engine.logTag, END_LINE)
        }
    }

    fun D(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.d(Engine.logTag, RESPONSE_UP_LINE)
            Log.d(Engine.logTag, CENTER_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.d(Engine.logTag, "$CENTER_LINE msg${i + 1} : " + msg[i].toString())
            }
            Log.d(Engine.logTag, END_LINE)
        }
    }

    fun httpLog(tag: String, msg: Any?) {
        Log.i(tag, msg.toString())
    }
}

