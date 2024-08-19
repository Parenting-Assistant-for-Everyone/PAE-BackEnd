package com.pae.server.chat.dto.request;

import com.pae.server.chat.domain.enums.ChatType;
import com.pae.server.chat.domain.enums.MessageType;

public record ChatSendReqDto(
        Long initiatorId,
        Long recipientId,
        Long goodsBoardId, // null 가능 -> 거래용 채팅일때만 필요
        Long chatRoomId,
        String message,
        ChatType chatType,
        MessageType messageType
) {
}
