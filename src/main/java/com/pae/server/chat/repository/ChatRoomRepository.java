package com.pae.server.chat.repository;

import com.pae.server.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByInitiatorIdAndRecipientIdAndGoodsBoardId(Long initiatorId, Long recipientId, Long goodsBoardId);
}
