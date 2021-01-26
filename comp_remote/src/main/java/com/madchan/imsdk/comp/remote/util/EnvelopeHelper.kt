package com.madchan.imsdk.comp.remote.util

import com.madchan.imsdk.comp.remote.bean.Envelope
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo

fun stuff(envelope: Envelope) : MessageVo? {
    return envelope.messageVo;
}

fun extract(messageVo: MessageVo) : Envelope {
    return Envelope(messageVo)
}

class EnvelopeHelper