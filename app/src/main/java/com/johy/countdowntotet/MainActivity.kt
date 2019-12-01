package com.johy.countdowntotet

import com.johy.countdowntotet.base.BaseActivity
import com.johy.countdowntotet.databinding.ActivityMainBinding
import com.johy.countdowntotet.defines.Constants.ONE_SECOND
import com.johy.countdowntotet.viewmodels.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onUIInitialized() {
    }

    override fun onWorksInitialized() {
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
