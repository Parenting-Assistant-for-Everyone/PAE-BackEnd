package com.pae.server.chat.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChatRoomRespDto(
        String otherMemberNickname,
        String otherMemberProfileUrl,
        String lastMessage,
        LocalDate lastChatDate,
        String thumbnailUrl
) {

    public static ChatRoomRespDto of(
            String otherMemberNickname, String otherMemberProfileUrl,
            String lastMessage, LocalDate lastChatDate,
            String thumbnailUrl
    ) {
        return ChatRoomRespDto.builder()
                .otherMemberNickname(otherMemberNickname)
                .otherMemberProfileUrl(otherMemberProfileUrl)
                .lastMessage(lastMessage)
                .lastChatDate(lastChatDate)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
