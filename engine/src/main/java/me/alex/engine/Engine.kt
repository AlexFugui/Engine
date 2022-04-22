package me.alex.engine

import me.alex.engine.log.LOG


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description: 项目配置
 * <p>
 * ================================================
 */
object Engine {
    /**
     * 设置调试模式
     */
    var isDebug: Boolean = true

    /**
     * 设置默认log Tag
     */
    var logTag: String = "Engine"

    /**
     * HttpLog的Tag
     */
    var httpLogTag: String = logTag

    /**
     * 网络框架每行最长log长度
     */
    var maxHttpLogSize: Int = 120
}