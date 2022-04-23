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
        "╔══════════════════════════════════════════════════════════════════════════════════════════"
    private const val CODE_LINE_LINE = "║ "
    private const val CENTER_LINE = "╠ "
    private const val END_LINE =
        "╚══════════════════════════════════════════════════════════════════════════════════════════"
    private const val N = "\n"

    @Synchronized
    fun I(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.i(Engine.logTag, RESPONSE_UP_LINE)
            Log.i(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.i(
                    Engine.logTag,
                    "$CENTER_LINE msg${i + 1} : " + msg[i].toString().replace(N, "")
                )
            }
            Log.i(Engine.logTag, END_LINE)
        }
    }

    @Synchronized
    fun II(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.i(Engine.logTag, RESPONSE_UP_LINE)
            Log.i(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            Log.i(Engine.logTag, "$CENTER_LINE msg  : " + msg.toString().replace(N, ""))
            Log.i(Engine.logTag, END_LINE)
        }
    }

    @Synchronized
    fun E(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.e(Engine.logTag, RESPONSE_UP_LINE)
            Log.e(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.e(
                    Engine.logTag,
                    "$CENTER_LINE msg${i + 1} : " + msg[i].toString().replace(N, "")
                )
            }
            Log.e(Engine.logTag, END_LINE)
        }
    }

    @Synchronized
    fun EE(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.e(Engine.logTag, RESPONSE_UP_LINE)
            Log.e(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            Log.e(Engine.logTag, "$CENTER_LINE msg  : " + msg.toString().replace(N, ""))
            Log.e(Engine.logTag, END_LINE)
        }
    }

    @Synchronized
    fun D(vararg msg: Any?) {
        if (Engine.isDebug) {
            Log.d(Engine.logTag, RESPONSE_UP_LINE)
            Log.d(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            for (i in msg.indices) {
                Log.d(
                    Engine.logTag,
                    "$CENTER_LINE msg${i + 1} : " + msg[i].toString().replace(N, "")
                )
            }
            Log.d(Engine.logTag, END_LINE)
        }
    }

    @Synchronized
    fun DD(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.d(Engine.logTag, RESPONSE_UP_LINE)
            Log.d(Engine.logTag, CODE_LINE_LINE + getCodeLine())
            Log.d(Engine.logTag, "$CENTER_LINE msg  : " + msg.toString().replace(N, ""))
            Log.d(Engine.logTag, END_LINE)
        }
    }


    fun httpLog(tag: String, msg: Any?) {
        Log.i(tag, msg.toString())
    }
}

