package com.johy.countdowntotet

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import com.johy.countdowntotet.base.BaseActivity
import com.johy.countdowntotet.databinding.ActivityMainBinding
import com.johy.countdowntotet.defines.Constants.ONE_SECOND
import com.johy.countdowntotet.viewmodels.MainViewModel

open class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {
    override val layoutId: Int
        get() = R.layout.activity_main

    open val isPortrait: Boolean
        get() {
            return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        }

    override fun onUIInitialized() {
        mViewModel?.parse()
    }

    override fun onWorksInitialized() {
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mViewModel?.mRunnable?.let { mViewModel?.mHandler?.postDelayed(it, ONE_SECOND) }
    }

    override fun onPause() {
        mViewModel?.mRunnable?.let { mViewModel?.mHandler?.removeCallbacks(it) }
        super.onPause()
    }
}
