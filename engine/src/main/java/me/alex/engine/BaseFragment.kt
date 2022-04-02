package me.alex.engine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<B : ViewDataBinding>(contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {
    lateinit var binding: B

    protected abstract fun initView()
    protected abstract fun initData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
        val baseActivity = (requireActivity() as? BaseActivity<*>)
        baseActivity?.onBackPressed(this::onBackPressed)

        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("Engine", "Initializing failure")
            e.printStackTrace()
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

}