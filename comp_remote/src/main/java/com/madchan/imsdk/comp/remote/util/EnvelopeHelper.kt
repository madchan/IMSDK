package com.madchan.imsdk.comp.remote.util

import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.mapper.MessagePOJOMapper
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO

class EnvelopeHelper {
    companion object {
        /**
         * 填充操作(VO->DTO)
         * @param envelope 信封类，包含消息视图对象
         */
        fun stuff(envelope: Envelope): MessageDTO.Message? {
            envelope?.messageVO?.apply {
                return MessagePOJOMapper.INSTANCE.vo2Dto(this).build()
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
                val messageVo = MessagePOJOMapper.INSTANCE.dto2Vo(this)
                envelope.messageVO = messageVo
                return envelope
            }
            return null
        }
    }
}