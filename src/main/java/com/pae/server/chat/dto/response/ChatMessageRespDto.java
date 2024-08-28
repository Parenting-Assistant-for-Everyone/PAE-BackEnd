package com.pae.server.chat.dto.response;

import com.pae.server.chat.domain.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageRespDto(
        String messageContent,
        LocalDateTime sendAt,
        Long senderId,
        boolean isRead
) {
    public static ChatMessageRespDto from(ChatMessage chatMessage) {
        return ChatMessageRespDto.builder()
                .messageContent(chatMessage.getMessageContent())
                .sendAt(chatMessage.getCreatedAt())
                .senderId(chatMessage.getSenderId())
                .isRead(chatMessage.getIsRead())
                .build();
    }
}
