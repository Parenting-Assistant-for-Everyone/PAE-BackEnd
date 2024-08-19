package com.pae.server.chat.domain;

import com.pae.server.chat.domain.enums.ChatType;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.common.domain.BaseEntity;
import com.pae.server.common.enums.BaseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Entity
@Table(name = "chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id; // 채팅방 ID

    @NotNull
    private Long initiatorId; // 대화를 시작한 유저의 id

    @NotNull
    private Long recipientId; // 메시지를 받은 유저의 id

    private Long goodsBoardId; // 연결되어있는 거래 게시판 id (null 허용)

    @NotNull
    @Enumerated(EnumType.STRING)
    private ChatType chatType; // 채팅방이 거래게시판인지 육아매칭인지 나타내는 필드

    @Enumerated(EnumType.STRING) @NotNull
    private BaseStatus baseStatus;

    @Builder
    public ChatRoom(Long initiatorId, Long recipientId, Long goodsBoardId, ChatType chatType) {
        this.initiatorId = initiatorId;
        this.recipientId = recipientId;
        this.goodsBoardId = goodsBoardId;
        this.chatType = chatType;
        this.baseStatus = BaseStatus.ACTIVATE;
    }

    public static ChatRoom from(ChatSendReqDto chatSendReqDto) {
        return ChatRoom.builder()
                .initiatorId(chatSendReqDto.initiatorId())
                .recipientId(chatSendReqDto.recipientId())
                .goodsBoardId(chatSendReqDto.goodsBoardId())
                .chatType(chatSendReqDto.chatType())
                .build();
    }
}
