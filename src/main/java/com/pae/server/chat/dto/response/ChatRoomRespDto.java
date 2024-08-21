package com.pae.server.chat.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChatRoomRespDto(
        Long chatRoomId,
        String otherMemberNickname,
        String otherMemberProfileUrl,
        String lastMessage,
        LocalDate lastChatDate,
        String thumbnailUrl,
        long notReadMessageCnt
) {

    public static ChatRoomRespDto of(
            Long chatRoomId,
            String otherMemberNickname, String otherMemberProfileUrl,
            String lastMessage, LocalDate lastChatDate,
            String thumbnailUrl, long unreadMessageCount
    ) {
        return ChatRoomRespDto.builder()
                .chatRoomId(chatRoomId)
                .otherMemberNickname(otherMemberNickname)
                .otherMemberProfileUrl(otherMemberProfileUrl)
                .lastMessage(lastMessage)
                .lastChatDate(lastChatDate)
                .thumbnailUrl(thumbnailUrl)
                .notReadMessageCnt(unreadMessageCount)
                .build();
    }
}
