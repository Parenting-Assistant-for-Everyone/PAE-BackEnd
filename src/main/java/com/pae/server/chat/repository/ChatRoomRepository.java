package com.pae.server.chat.repository;

import com.pae.server.chat.domain.ChatRoom;
import com.pae.server.chat.repository.custom.ChatRoomCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomCustomRepository {
    Optional<ChatRoom> findByInitiatorIdAndRecipientIdAndGoodsBoardId(Long initiatorId, Long recipientId, Long goodsBoardId);

    List<ChatRoom> findAllByIsActivateTrueAndInitiatorIdOrRecipientId(Long initiatorId, Long recipientId);

    /***
     * 메시지를 수신받을 사람의 PK 값을 얻어오는 쿼리
     * @param chatRoomId : 채팅방 PK
     * @param senderId : 메시지 송신자의 PK
     * @return : 메시지 수신자의 PK
     */
    @Query("SELECT CASE WHEN c.initiatorId = :senderId THEN c.recipientId ELSE c.initiatorId END " +
            "FROM ChatRoom c " +
            "WHERE c.id = :chatRoomId AND (c.initiatorId = :senderId OR c.recipientId = :senderId)")
    Long findRecipientIdByChatRoomIdAndSenderId(@Param("chatRoomId") Long chatRoomId, @Param("senderId") Long senderId);
}
