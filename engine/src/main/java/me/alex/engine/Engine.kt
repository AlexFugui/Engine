package me.alex.engine


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
    var isDebug = true

    /**
     * 设置默认log Tag
     */
    var logTag = "Engine"

    /**
     * 除了logTag还可以自定义一个tag在msg中 默认为空
     */
    var msgTag = ""

    /**
     * HttpLog的Tag
     */
    var httpLogTag = logTag

    /**
     * 网络框架每行最长log长度
     */
    var maxHttpLogSize = 120
}