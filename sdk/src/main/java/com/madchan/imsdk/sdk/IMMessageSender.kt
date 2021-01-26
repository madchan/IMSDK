package com.madchan.imsdk.sdk

import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo

object IMMessageSender {
    fun sendMessage(messageVo: MessageVo) {
        MessageAccessServiceProvider.sendMessage(messageVo)
    }
}