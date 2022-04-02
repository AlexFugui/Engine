package me.alex.engine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private val onBackPressInterceptors = ArrayList<() -> Boolean>()

    lateinit var mBinding: B
    lateinit var rootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    /**
     * 返回键事件
     * @param block 返回值表示是否拦截事件
     */
    @Deprecated("建议使用onBackPressedDispatcher")
    fun onBackPressed(block: () -> Boolean) {
        onBackPressInterceptors.add(block)
    }
}