package com.pae.server.chat.dto.request;

import com.pae.server.chat.domain.enums.ChatType;

public record ChatRoomCreateReqDto(
        Long initiatorId,
        Long recipientId,
        Long goodsBoardId,
        ChatType chatType
) {
}
