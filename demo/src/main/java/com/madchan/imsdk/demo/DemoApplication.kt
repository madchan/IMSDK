package com.madchan.imsdk.demo

import android.app.Application
import com.madchan.imsdk.sdk.IMClient
import com.madchan.imsdk.comp.base.SDKOptions

class DemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        IMClient.init(this, initSDKOptions())
    }

    private fun initSDKOptions(): SDKOptions {
        val sdkOptions = SDKOptions()
        sdkOptions.appKey = "mock_app_key"
        return sdkOptions
    }
}