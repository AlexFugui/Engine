package me.alex.engine.base

import android.app.Application
import android.content.Context


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
abstract class BaseApplication : Application() {
    protected lateinit var me: Context

    override fun onCreate() {
        super.onCreate()
        me = this
        init()
    }

    abstract fun init()

}