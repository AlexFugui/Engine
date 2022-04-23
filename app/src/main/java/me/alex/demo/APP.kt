package me.alex.demo

import android.app.ProgressDialog
import com.drake.net.NetConfig
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.okhttp.*
import com.drake.net.request.BaseRequest
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.WaitDialog
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
        DialogX.init(me)
        Engine.apply {
            //调试模式
            isDebug = BuildConfig.DEBUG
            //日志log tag
            logTag = "Engine"
            //网络请求日志tag 默认和logTag一致
            httpLogTag = "Engine"
        }

        NetConfig.initialize("https://www.wanandroid.com/") {
            trustSSLCertificate() // 信任所有证书

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

            setConverter(GsonConverter()) // 数据转换器

            setDialogFactory { // 全局加载对话框
                ProgressDialog(it).apply {
                    setMessage("加载中...")
                }
            }
        }
    }

}