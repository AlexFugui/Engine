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
    private var TAG = Engine.logTag
    private var msgTAG = ""
    fun I(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.i(tag, msgTAG + getCodeLine() + msg.toString())
        }
    }

    fun I(msg: Any?) {
        if (Engine.isDebug) {
            Log.i(TAG, msgTAG + getCodeLine() + msg.toString())
        }
    }

    fun E(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.e(tag, msgTAG + getCodeLine() + msg.toString())
        }
    }

    fun E(msg: Any?) {
        if (Engine.isDebug) {
            Log.e(TAG, msgTAG + getCodeLine() + msg.toString())
        }
    }

    fun D(tag: String, msg: Any?) {
        if (Engine.isDebug) {
            Log.d(tag, msgTAG + getCodeLine() + msg.toString())
        }
    }

    fun D(msg: Any?) {
        if (Engine.isDebug) {
            Log.d(TAG, msgTAG + getCodeLine() + msg.toString())
        }
    }
}

