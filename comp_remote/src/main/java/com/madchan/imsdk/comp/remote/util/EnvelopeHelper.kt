package com.madchan.imsdk.comp.remote.util

import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.comp.remote.mapper.MessageEntityMapper
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO

class EnvelopeHelper {
    companion object {
        /**
         * 填充操作(VO->DTO)
         * @param envelope 信封类，包含消息视图对象
         */
        fun stuff(envelope: Envelope): MessageDTO.Message? {
            return envelope.messageVO?.run {
                MessageEntityMapper.INSTANCE.vo2Dto(this).build()
            } ?: null
        }

        /**
         * 提取操作(DTO->VO)
         * @param messageDTO 消息数据传输对象
         */
        fun extract(messageDTO: MessageDTO.Message): Envelope? {
            with(Envelope()) {
                messageVO = MessageEntityMapper.INSTANCE.dto2Vo(messageDTO)
                return this
            }
        }
    }
}