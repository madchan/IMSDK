package com.madchan.imsdk.comp.base

import android.content.Context
import kotlin.properties.Delegates

/**
 * ## SDK缓存
 * 用于缓存向全局提供的对象
 */
class SDKCache {
    companion object {
        /** 接入方的应用上下文 */
        lateinit var context : Context
        /** SDK配置选项 */
        lateinit var sdkOptions: SDKOptions
        /** 用户身份令牌 */
        lateinit var token: String
    }
}