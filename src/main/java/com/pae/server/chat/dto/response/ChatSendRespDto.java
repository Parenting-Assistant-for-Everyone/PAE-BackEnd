package com.pae.server.chat.dto.response;

import com.pae.server.chat.domain.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatSendRespDto(
        String messageContent,
        LocalDateTime sendAt,
        Long senderId,
        Long otherUserId,
        Integer unreadMessageCount
) {
    public static ChatSendRespDto from(ChatMessage chatMessage, Long otherUserId, Integer unreadMessageCount) {
        return ChatSendRespDto.builder()
                .messageContent(chatMessage.getMessageContent())
                .sendAt(chatMessage.getCreatedAt())
                .senderId(chatMessage.getSenderId())
                .otherUserId(otherUserId)
                .unreadMessageCount(unreadMessageCount)
                .build();
    }
}
