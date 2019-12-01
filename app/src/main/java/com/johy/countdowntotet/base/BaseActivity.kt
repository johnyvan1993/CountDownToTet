package com.johy.countdowntotet.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.johy.countdowntotet.BR
import com.johy.countdowntotet.R
import com.johy.countdowntotet.helper.Navigator

abstract class BaseActivity<V : ViewDataBinding, M : BaseViewModel>(private val clazzM: Class<M>) :
    AppCompatActivity(), Observer<Boolean> {
    abstract val layoutId: Int
//    abstract val isDisplayRightMenu: Boolean
//    abstract val canBack: Boolean
    abstract fun onUIInitialized()
    abstract fun onWorksInitialized()

    var childFragmentManager: FragmentManager? = null
    var haveNotification = false
    var mBinding: V? = null
    var mViewModel: M? = null
    var mNavigator: Navigator<BaseActivity<V, M>>? = null
    var mNavigationListener: (() -> Unit)? = {
        onBackPressed()
    }
    var countOnBack: Int = 1
        get() = if (field > 2) 1 else field

    override fun onCreate(savedInstanceState: Bundle?) {
        mNavigator = Navigator(this)
        super.onCreate(savedInstanceState)
        setupBinding()
        onUIInitialized()
        onWorksInitialized()
    }

    private fun setupBinding() {
        mViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(clazzM)
        mViewModel?.loadingLive?.observe(this, this)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        mBinding?.lifecycleOwner = this
        mBinding?.setVariable(BR.model, mViewModel)
        mBinding?.setVariable(BR.context, this)
    }

    override fun onChanged(showLoading: Boolean?) {
    }

    override fun onDestroy() {
        mViewModel = null
        mBinding = null
        mNavigator?.release()
        mViewModel?.loadingLive?.removeObserver(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (childFragmentManager != null && childFragmentManager!!.backStackEntryCount > 0) {
            val lastestId =
                childFragmentManager!!.getBackStackEntryAt(childFragmentManager!!.backStackEntryCount - 1)
                    .id
            childFragmentManager!!.popBackStack(lastestId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            val lastestId =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                    .id
            supportFragmentManager.popBackStack(lastestId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else if (countOnBack == 2) {
            moveTaskToBack(true)
            ++countOnBack
        } else if (countOnBack == 1) {
            Toast.makeText(this, R.string.alert_on_back_press, Toast.LENGTH_SHORT).show()
            ++countOnBack
        } else super.onBackPressed()
    }
}