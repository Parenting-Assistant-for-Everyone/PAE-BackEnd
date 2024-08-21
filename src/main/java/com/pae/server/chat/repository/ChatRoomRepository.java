package com.pae.server.chat.repository;

import com.pae.server.chat.domain.ChatRoom;
import com.pae.server.chat.repository.custom.ChatRoomCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomCustomRepository {
    Optional<ChatRoom> findByInitiatorIdAndRecipientIdAndGoodsBoardId(Long initiatorId, Long recipientId, Long goodsBoardId);

    List<ChatRoom> findAllByIsActivateTrueAndInitiatorIdOrRecipientId(Long initiatorId, Long recipientId);
}
