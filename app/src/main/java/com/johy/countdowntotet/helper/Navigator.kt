package com.johy.countdowntotet.helper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class Navigator<T : Context>(private var mContext: T?) {

    private fun navigate(intent: Intent, forResult: Boolean = false, requestCode: Int = 0) {
        if (forResult) (mContext as AppCompatActivity).startActivityForResult(intent, requestCode)
        else mContext?.startActivity(intent)
    }

    fun release() {
        mContext = null
    }
}