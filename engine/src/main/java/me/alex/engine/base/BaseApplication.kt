package me.alex.engine.base

import android.app.Application


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

    override fun onCreate() {
        super.onCreate()
        init()
    }

    abstract fun init()

}