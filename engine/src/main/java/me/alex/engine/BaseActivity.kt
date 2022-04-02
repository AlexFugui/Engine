package me.alex.engine

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KFunction0


abstract class BaseActivity<B : ViewDataBinding>(contentLayoutId: Int = 0) :
    AppCompatActivity(contentLayoutId) {
    private val onBackPressInterceptors = ArrayList<() -> Boolean>()

    lateinit var binding: B
    lateinit var rootView: View

    override fun setContentView(layoutResId: Int) {
        rootView = layoutInflater.inflate(layoutResId, null)
        setContentView(rootView)
        binding = DataBindingUtil.bind(rootView)!!
        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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