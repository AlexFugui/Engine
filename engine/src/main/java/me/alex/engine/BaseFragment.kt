package me.alex.engine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var mBinding: VB? = null
    private val binding: VB get() = mBinding!!
    private lateinit var me: BaseActivity<*>

    protected abstract fun initView()
    protected abstract fun initData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        try {
            me= activity as BaseActivity<*>
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("Engine", "Initializing failure")
            e.printStackTrace()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding?.let {
            mBinding = null
        }
    }

}