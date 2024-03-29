package com.johy.countdowntotet

import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
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

    private var mPlayer: MediaPlayer? = null

    override fun onUIInitialized() {
        mPlayer = MediaPlayer.create(this, R.raw.tet_nguyen_dan)
        mViewModel?.parse()
    }

    override fun onWorksInitialized() {
    }

    override fun onStart() {
        super.onStart()
        intent.extras?.getInt("paused_time")?.let { mPlayer?.seekTo(it) }
        if (mPlayer?.isPlaying != true) mPlayer?.start()
        mViewModel?.mRunnable?.let { mViewModel?.mHandler?.postDelayed(it, ONE_SECOND) }
    }

    override fun onPause() {
        mViewModel?.mRunnable?.let { mViewModel?.mHandler?.removeCallbacks(it) }
        if (mPlayer?.isPlaying == true) mPlayer?.pause()
        val bundle = intent.extras ?: Bundle()
        bundle.putInt("paused_time", mPlayer?.currentPosition ?: 0)
        intent.putExtras(bundle)
        super.onPause()
    }

    override fun onDestroy() {
        if (mPlayer?.isPlaying == true) mPlayer?.stop()
        mPlayer?.release()
        mPlayer = null
        super.onDestroy()
    }
}
