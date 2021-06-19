package com.madchan.imsdk.sdk

import com.madchan.imsdk.comp.remote.MessageAccessServiceProvider
import com.madchan.imsdk.lib.objects.bean.vo.MessageVO

object IMMessageSender {

    fun sendMessage(messageVO: MessageVO) {
        MessageAccessServiceProvider.sendMessage(messageVO)
    }

}