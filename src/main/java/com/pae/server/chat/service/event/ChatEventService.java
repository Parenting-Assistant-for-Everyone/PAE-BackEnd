package com.pae.server.chat.service.event;

public interface ChatEventService {
    void markMessagesAsRead(Long chatRoomId, Long userId);

}
