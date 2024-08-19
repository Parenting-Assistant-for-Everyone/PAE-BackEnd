package com.pae.server.chat.service;

import com.pae.server.chat.domain.ChatMessage;
import com.pae.server.chat.domain.ChatRoom;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;
import com.pae.server.chat.repository.mongo.ChatMessageRepository;
import com.pae.server.chat.repository.ChatRoomRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatSendRespDto processMessage(ChatSendReqDto chatSendReqDto) {
        // 1. 채팅방이 존재하는지 확인
        ChatRoom chatRoom;
        if (chatSendReqDto.chatRoomId() == null) {
            // 첫 채팅이 시작된 채팅방 -> 채팅방을 생성해줘야함.
            chatRoom = createChatRoom(chatSendReqDto);
        } else {
            // 채팅방이 존재하는 경우
            chatRoom = validGoodsChatRoom(chatSendReqDto.initiatorId(), chatSendReqDto.recipientId(), chatSendReqDto.goodsBoardId());
        }

        // 2. 메시지 저장
        return ChatSendRespDto.from(saveChatMessage(chatSendReqDto, chatRoom.getId()));
    }

    private ChatMessage saveChatMessage(ChatSendReqDto chatSendReqDto, Long trustChatRoomId) {
        return chatMessageRepository.save(ChatMessage.of(chatSendReqDto, trustChatRoomId));
    }

    private ChatRoom validGoodsChatRoom(Long initiatorId, Long recipientId, Long goodsBoardId) {
        return chatRoomRepository.findByInitiatorIdAndRecipientIdAndGoodsBoardId(
                initiatorId, recipientId, goodsBoardId
        ).orElseThrow(() -> new CustomException(CustomResponseStatus.GOODS_CHAT_NOT_FOUND));
    }

    private ChatRoom createChatRoom(ChatSendReqDto chatSendReqDto) {
        return chatRoomRepository.save(ChatRoom.from(chatSendReqDto));
    }

}
