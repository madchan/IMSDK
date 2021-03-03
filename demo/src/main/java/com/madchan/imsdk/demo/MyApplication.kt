package com.madchan.imsdk.demo

import android.app.Application
import com.madchan.imsdk.sdk.IMSDKClient

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        IMSDKClient.init(this)
    }
}