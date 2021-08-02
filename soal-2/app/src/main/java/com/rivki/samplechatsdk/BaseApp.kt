package com.rivki.samplechatsdk

import android.app.Application
import com.qiscus.sdk.chat.core.QiscusCore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application(){
    override fun onCreate() {
        super.onCreate()
        QiscusCore.setup(this, "sdksample")
    }
}