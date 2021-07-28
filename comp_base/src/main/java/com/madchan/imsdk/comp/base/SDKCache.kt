package com.madchan.imsdk.comp.base

import android.content.Context
import kotlin.properties.Delegates

class SDKCache {
    companion object {
        /** 接入方的应用上下文 */
        lateinit var context : Context
        /** SDK配置选项 */
        lateinit var sdkOptions: SDKOptions
        /** 接入方的当前用户Token */
        lateinit var token: String
    }
}