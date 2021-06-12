package com.madchan.imsdk.comp.remote.util

import com.google.protobuf.ByteString
import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo

class EnvelopeHelper {
    companion object {
        /**
         * 填充操作(VO->DTO)
         * @param envelope 信封类，包含消息视图对象
         */
        fun stuff(envelope: Envelope): MessageDTO.Message? {
            envelope?.messageVo?.apply {
                return MessageDTO.Message.newBuilder()
                    .setMessageId(messageId)
                    .setMessageType(MessageDTO.Message.MessageType.forNumber(messageType))
                    .setSenderId(sendId)
                    .setTargetId(targetId)
                    .setTimestamp(timestamp)
                    .setContent(ByteString.copyFromUtf8(content))
                    .build()
            }
            return null
        }

        /**
         * 提取操作(DTO->VO)
         * @param messageDTO 消息数据传输对象
         */
        fun extract(messageDTO: MessageDTO.Message): Envelope? {
            messageDTO?.apply {
                val envelope = Envelope()
                val messageVo = MessageVo(
                    messageId = messageId,
                    messageType = messageType.number,
                    sendId = senderId,
                    targetId = targetId,
                    timestamp = timestamp,
                    content = String(content.toByteArray())
                )
                envelope.messageVo = messageVo
                return envelope
            }
            return null
        }
    }
}