package com.pae.server.chat.domain;

import com.pae.server.common.domain.BaseEntity;
import com.pae.server.common.enums.BaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id; // 채팅방 ID

    private Long initiatorId; // 대화를 시작한 유저의 id

    private Long recipientId; // 메시지를 받은 유저의 id

    @Enumerated(EnumType.STRING)
    private BaseStatus baseStatus;
}
