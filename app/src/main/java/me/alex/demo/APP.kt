package me.alex.demo

import android.app.ProgressDialog
import com.drake.net.NetConfig
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDialogFactory
import com.drake.net.okhttp.setLog
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.net.request.BaseRequest
import me.alex.engine.Engine
import me.alex.engine.base.BaseApplication
import me.alex.engine.converter.GsonConverter
import me.alex.engine.converter.MoshiConverter
import me.alex.engine.interceptor.BaseLogInterceptor
import me.alex.engine.log.LOG
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
class APP : BaseApplication() {
    override fun init() {
        LOG.I("APP init")

        Engine.apply {
            //调试模式
            isDebug = BuildConfig.DEBUG
            //日志log tag
            logTag = "LogTag"
            //额外的logTag 方便多人开发添加自己或固定位置的tag
            msgTag = "MsgTag"
            //网络请求日志tag 默认和logTag一致
            httpLogTag = "AlexHttp"
        }

        NetConfig.initialize("https://www.secxiot.top:9000/consumer/deviceApp/da/") {

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            setLog(BuildConfig.DEBUG) // LogCat异常日志
            addInterceptor(BaseLogInterceptor(BuildConfig.DEBUG)) // 添加日志记录器
            setRequestInterceptor(object : RequestInterceptor { // 添加请求拦截器
                override fun interceptor(request: BaseRequest) {
//                    request.addHeader("client", "Net")
//                    request.setHeader("token", "123456")
                }
            })

            setConverter(MoshiConverter()) // 数据转换器

            setDialogFactory { // 全局加载对话框
                ProgressDialog(it).apply {
                    setMessage("我是全局自定义的加载对话框...")
                }
            }
        }
    }

}