package com.pae.server.chat.dto.response;

import com.pae.server.chat.dto.request.ChatRoomCreateReqDto;
import lombok.Builder;

@Builder
public record ChatRoomCreateRespDto(
        Long newChatRoomId
) {
    public static ChatRoomCreateRespDto from(Long newChatRoomId) {
        return ChatRoomCreateRespDto.builder()
                .newChatRoomId(newChatRoomId)
                .build();
    }
}
