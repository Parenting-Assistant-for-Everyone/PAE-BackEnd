package com.pae.server.chat.dto.response;

import com.pae.server.chat.domain.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatSendRespDto(
        String messageContent,
        LocalDateTime sendAt,
        Long senderId,
        Long chatRoomId
) {
    public static ChatSendRespDto from(ChatMessage chatMessage) {
        return ChatSendRespDto.builder()
                .messageContent(chatMessage.getMessageContent())
                .sendAt(chatMessage.getCreatedAt())
                .senderId(chatMessage.getSenderId())
                .chatRoomId(chatMessage.getChatRoomId())
                .build();
    }
}
