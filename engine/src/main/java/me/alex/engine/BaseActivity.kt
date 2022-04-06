package me.alex.engine

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private lateinit var mBinding: B
    private lateinit var rootView: View
    private lateinit var me: Context


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