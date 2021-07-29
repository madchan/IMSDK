package com.madchan.imsdk.sdk

import android.content.Context
import com.madchan.imsdk.comp.base.SDKCache
import com.madchan.imsdk.comp.base.SDKOptions
import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider

/**
 * SDK核心类
 */
object IMClient{

    /**
     * 初始化SDK，建议在Application实现类中调用。
     * @param context 上下文对象
     * @param sdkOptions    SDK配置选项
     */
    fun init(context: Context, sdkOptions: SDKOptions) {
        SDKCache.context = context.applicationContext
        SDKCache.sdkOptions = sdkOptions
    }

    /**
     * 连接聊天服务器，需在init(Context, SDKOptions)之后调用
     * @param token 用户身份令牌
     */
    fun connect(token: String) {
        SDKCache.token = token
        MessageAccessServiceProvider.getWebSocketServer()
    }

}