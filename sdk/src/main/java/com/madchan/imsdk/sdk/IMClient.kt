package com.madchan.imsdk.sdk

import android.content.Context
import com.madchan.imsdk.comp.base.SDKCache
import com.madchan.imsdk.comp.base.SDKOptions
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider

object IMClient{

    fun init(context: Context, sdkOptions: SDKOptions) {
        SDKCache.context = context.applicationContext
        SDKCache.sdkOptions = sdkOptions
    }

    fun connect(token: String) {
        SDKCache.token = token
        MessageAccessServiceProvider.getWebSocketServer()
    }

}