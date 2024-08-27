package com.pae.server.chat.repository.mongo;

import com.pae.server.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Optional<ChatMessage> findTopByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);

    List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);

    // 현재 사용자가 읽지 않은 메시지의 개수를 세는 메서드
    long countByChatRoomIdAndSenderIdNotAndIsReadFalse(Long chatRoomId, Long memberId);

    // 특정 채팅방에서 특정 수신자가 읽지 않은 메시지 조회
    @Query("{ 'chatRoomId': ?0, 'senderId': ?1, 'isRead': false }")
    List<ChatMessage> findUnreadMessages(Long chatRoomId, Long receiverId);
}
