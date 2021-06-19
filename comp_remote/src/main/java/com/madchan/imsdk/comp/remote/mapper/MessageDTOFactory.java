package com.madchan.imsdk.comp.remote.mapper;

import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO;

public class MessageDTOFactory {

    public MessageDTO.Message.Builder createMessageDto() {
        return MessageDTO.Message.newBuilder();
    }
}
