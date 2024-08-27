package com.pae.server.chat.service.event;

import com.pae.server.chat.domain.ChatMessage;
import com.pae.server.chat.repository.ChatRoomRepository;
import com.pae.server.chat.repository.mongo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatEventServiceImpl implements ChatEventService{
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void markMessagesAsRead(Long chatRoomId, Long senderId) {
        Long receiverId = chatRoomRepository.findRecipientIdByChatRoomIdAndSenderId(chatRoomId, senderId);

        // 읽지 않은 메시지를 조회
        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(chatRoomId, receiverId);

        // 메시지 읽음 처리
        unreadMessages.forEach(ChatMessage::updateUnReadToRead);

        // 업데이트된 메시지 저장
        chatMessageRepository.saveAll(unreadMessages);
    }
}
