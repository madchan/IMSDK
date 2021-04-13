package com.madchan.imsdk.sdk

import android.content.Context
import com.madchan.imsdk.comp.base.SDKBase

object IMSDKClient{

    fun init(context: Context) {
        SDKBase.dependentContext = context.applicationContext
    }

}