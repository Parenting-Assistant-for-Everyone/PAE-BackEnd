package com.pae.server.chat.domain;

import com.pae.server.chat.domain.enums.MessageType;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.common.domain.BaseEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_message")
@Getter
public class ChatMessage extends BaseEntity {
    @Id
    private String id; // MongoDB에서 사용하는 고유 ID

    private Long chatRoomId; // 채팅방 ID (MySQL과 연동)

    private Long senderId; // 메시지 발신자 ID

    private String messageContent; // 메시지 내용

    @Enumerated(EnumType.STRING)
    private MessageType messageType; // 메시지 타입 (text, image, etc.)

    private Boolean isRead; // 읽음 여부

    @Builder
    public ChatMessage(Long chatRoomId, Long senderId, String messageContent, MessageType messageType, boolean isRead) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.messageContent = messageContent;
        this.messageType = messageType;
        this.isRead = isRead;
    }

    public static ChatMessage of(ChatSendReqDto chatSendReqDto, Long trustChatRoomId, boolean isRead) {
        return ChatMessage.builder()
                .chatRoomId(trustChatRoomId)
                .senderId(chatSendReqDto.senderId())
                .messageContent(chatSendReqDto.message())
                .messageType(chatSendReqDto.messageType())
                .isRead(isRead)
                .build();
    }
}
