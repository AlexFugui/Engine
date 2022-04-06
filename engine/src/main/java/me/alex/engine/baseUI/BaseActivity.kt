package me.alex.engine.baseUI

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: B
    lateinit var me: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        me = this
        initVB()
        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initVB() {
        mBinding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(mBinding.root)
    }

    protected abstract fun initView()

    protected abstract fun initData()

}