package com.madchan.imsdk.sdk

import android.content.Context
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider

object IMSDKClient {

    fun init(context: Context) {
        MessageAccessServiceProvider.setupService(context)
    }
}