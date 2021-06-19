package com.madchan.imsdk.sdk

import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import com.madchan.imsdk.lib.objects.bean.vo.MessageVO

object IMMessageCreator {

    fun createTextMessage(
        sendId: String,
        targetId: String,
        content: String
    ): MessageVO {
        return MessageVO(
            messageId = System.currentTimeMillis(),
            messageType = MessageDTO.Message.MessageType.MESSAGE_TYPE_TEXT_VALUE,
            senderId = sendId,
            targetId = targetId,
            timestamp = System.currentTimeMillis(),
            content = content
        )
    }

}