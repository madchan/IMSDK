package com.madchan.imsdk.comp.remote.mapper;

import com.google.protobuf.ByteString;
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO;
import com.madchan.imsdk.lib.objects.bean.vo.MessageVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MessageDTOFactory.class)
public interface MessageEntityMapper {

    MessageEntityMapper INSTANCE = Mappers.getMapper(MessageEntityMapper.class);

    @Mapping(source = "messageType", target = "messageTypeValue")
    @Mapping(target = "mergeFrom", ignore = true)
    @Mapping(target = "senderIdBytes", ignore = true)
    @Mapping(target = "targetIdBytes", ignore = true)
    MessageDTO.Message.Builder vo2Dto(MessageVO messageVo);

    MessageVO dto2Vo(MessageDTO.Message messageDto);

    @Mapping(source = "messageTypeValue", target = "messageType")
    default MessageDTO.Message.MessageType int2Enum(int value) {
        return MessageDTO.Message.MessageType.forNumber(value);
    }

    default int enum2Int(MessageDTO.Message.MessageType type) {
        return type.getNumber();
    }

    default String byte2String(ByteString byteString) {
        return new String(byteString.toByteArray());
    }

    default ByteString string2Byte(String string) {
        return ByteString.copyFrom(string.getBytes());
    }
}
