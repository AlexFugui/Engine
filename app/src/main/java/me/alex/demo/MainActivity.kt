package me.alex.demo

import me.alex.demo.databinding.ActivityMainBinding
import me.alex.engine.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView() {
        mBinding.mainTxt.text = "base test"
    }

    override fun initData() {

    }

}