package me.alex.demo

import androidx.collection.arraySetOf
import androidx.fragment.app.commit
import com.drake.net.Get
import com.drake.net.Net
import com.drake.net.Net.put
import com.drake.net.utils.scope
import com.drake.net.utils.scopeNetLife
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import me.alex.demo.databinding.ActivityMainBinding
import me.alex.engine.Engine
import me.alex.engine.base.BaseActivity
import me.alex.engine.log.LOG
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    val fragment1: D1Fragment = D1Fragment()
    val fragment2: D2Fragment = D2Fragment()
    override fun initView() {
        mBinding.mainTxt.text = "base test"
        supportFragmentManager.commit {
            add(R.id.main_frag, fragment1)
            add(R.id.main_frag, fragment2)
            hide(fragment2)
            show(fragment1)
        }
    }

    override fun initData() {
        LOG.I("test logger")
        val array = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
        LOG.I(array)
        LOG.I(TestData("狗蛋", 18, Locale.getDefault().country))


        scopeNetLife {
            mBinding.mainTxt.text = Get<String>("deviceRegister") {
                setQuery("deviceId", "SECX238CFCA02139DB")
                setQuery("cellId", "")
                setQuery("iccId", "89860455261990047903")
                setQuery("latitude", "")
                setQuery("longitude", "")

//                Json {
//                    JsonObject.run {
//                        put("deviceId", "SECX238CFCA02139DB")
//                        put("cellId", "")
//                        put("latitude", "")
//                        put("longitude", "")
//                    }
//                }
            }.await()
        }
    }
}