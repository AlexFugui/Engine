package me.alex.demo

import androidx.collection.arraySetOf
import androidx.fragment.app.commit
import me.alex.demo.databinding.ActivityMainBinding
import me.alex.engine.Engine
import me.alex.engine.base.BaseActivity
import me.alex.engine.log.LOG

class MainActivity : BaseActivity<ActivityMainBinding>() {
    val fragment1: D1Fragment = D1Fragment()
    val fragment2: D2Fragment = D2Fragment()
    override fun initView() {
        mBinding.mainTxt.text = "base test"
    }

    override fun initData() {
        LOG.I("test logger")
        val array = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
        LOG.I(array)
        LOG.I(TestData("狗蛋", 18, "北京"))
        supportFragmentManager.commit {
            add(R.id.main_frag, fragment1)
            add(R.id.main_frag, fragment2)
            hide(fragment2)
            show(fragment1)
        }
        Engine.apply {
            isDebug = true
            logTag = "test"
        }
    }
}