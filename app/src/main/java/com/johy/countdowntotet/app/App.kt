package com.johy.countdowntotet.app

import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        iApplication = this
    }

    companion object {
        var iApplication: App? = null
    }
}
