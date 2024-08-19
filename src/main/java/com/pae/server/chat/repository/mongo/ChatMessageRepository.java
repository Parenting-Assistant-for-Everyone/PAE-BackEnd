package com.pae.server.chat.repository.mongo;

import com.pae.server.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
