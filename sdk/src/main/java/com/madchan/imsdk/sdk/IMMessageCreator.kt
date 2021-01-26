package com.madchan.imsdk.sdk

import com.madchan.imsdk.lib.objects.bean.vo.MessageVo



object IMMessageCreator {
    fun createTextMessage(content: CharSequence) : MessageVo {
        return MessageVo(content)
    }
}