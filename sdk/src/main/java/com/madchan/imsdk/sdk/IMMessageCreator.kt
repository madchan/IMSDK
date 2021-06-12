package com.madchan.imsdk.sdk

import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo
import java.util.*

object IMMessageCreator {

    fun createTextMessage(
        sendId: String,
        targetId: String,
        content: String
    ): MessageVo {
        return MessageVo(
            messageId = System.currentTimeMillis(),
            messageType = MessageDTO.Message.MessageType.MESSAGE_TYPE_TEXT_VALUE,
            sendId = sendId,
            targetId = targetId,
            timestamp = System.currentTimeMillis(),
            content = content
        )
    }

}