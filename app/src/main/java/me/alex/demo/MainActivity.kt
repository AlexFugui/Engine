package me.alex.demo

import androidx.collection.arraySetOf
import androidx.fragment.app.commit
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
import me.alex.demo.databinding.ActivityMainBinding
import me.alex.engine.base.BaseActivity
import me.alex.engine.log.LOG
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val fragment1: D1Fragment = D1Fragment()
    private val fragment2: D2Fragment = D2Fragment()
    private lateinit var reg: BaseResponse<Reg>
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
        val array = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
        LOG.I(array)
        LOG.I(TestData("狗蛋", 18, Locale.getDefault().country))

        scopeNetLife {
            reg = Get<BaseResponse<Reg>>("deviceRegister") {
                setQuery("deviceId", "SECX238CFCA02139DB")
                setQuery("cellId", "")
                setQuery("iccId", "89860455261990047903")
                setQuery("latitude", "")
                setQuery("longitude", "")
            }.await()
            LOG.I("reg", reg, reg)
        }

        LOG.I("reg", "msg1", "msg2")

//        LOG.I("reg", reg, reg, reg, reg)
    }
}