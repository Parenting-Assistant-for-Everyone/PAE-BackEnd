package com.pae.server.chat.dto.request;

import com.pae.server.chat.domain.enums.MessageType;

public record ChatSendReqDto(
        String message,
        MessageType messageType,
        Long senderId
) {
}
