package com.pae.server.chat.service;

import com.pae.server.chat.dto.request.ChatRoomCreateReqDto;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatMessageRespDto;
import com.pae.server.chat.dto.response.ChatRoomCreateRespDto;
import com.pae.server.chat.dto.response.ChatRoomRespDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;

import java.util.List;

public interface ChatService {
    ChatRoomCreateRespDto createChatRoom(ChatRoomCreateReqDto chatRoomCreateReqDto);

    ChatSendRespDto sendMessage(ChatSendReqDto chatSendReqDto, Long chatRoomId);

    List<ChatRoomRespDto> queryChatRooms(Long memberId);

    List<ChatMessageRespDto> queryChatMessages(Long chatRoomId);
}
