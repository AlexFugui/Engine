package me.alex.demo

import androidx.fragment.app.commit
import com.drake.net.Get
import com.drake.net.utils.scopeDialog
import me.alex.demo.databinding.ActivityMainBinding
import me.alex.engine.base.BaseActivity
import me.alex.engine.log.LOG

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val fragment1: D1Fragment = D1Fragment()
    private val fragment2: D2Fragment = D2Fragment()
    private lateinit var result: String
    override fun initView() {
        mBinding.mainTxt.text = "base test"
        mBinding.mainTxt.setOnClickListener {
            LOG.I("test", TestData("刘德华", 21, "地址"))
        }
        supportFragmentManager.commit {
            add(R.id.main_frag, fragment1)
            add(R.id.main_frag, fragment2)
            hide(fragment2)
            show(fragment1)
        }
        mBinding.mainFrag.setOnClickListener {
            scopeDialog {
                result = Get<String>("http://api.wpbom.com/api/ancien.php") {
                    setQuery("msg", "静夜思")
                    setQuery("b", 1)
                }.await()
                LOG.I(result)
            }

            scopeDialog {
                result = Get<String>("/article/list/0/json") {
                }.await()
                LOG.I(result)
            }
        }
    }

    override fun initData() {

    }
}