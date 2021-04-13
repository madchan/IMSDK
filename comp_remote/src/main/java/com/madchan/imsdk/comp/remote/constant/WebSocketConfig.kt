package com.madchan.imsdk.comp.remote.constant

/**
 * WebSocket配置类
 */
class WebSocketConfig {
    companion object {
        /** 消息版本。当低版本的客户端收到高版本的消息包并且无法处理时，则需要提醒用户升级最新版本  */
        const val MESSAGE_VERSION = 1.0f
    }
}