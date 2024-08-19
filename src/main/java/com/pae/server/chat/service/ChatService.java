package com.pae.server.chat.service;

import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatMessageRespDto;
import com.pae.server.chat.dto.response.ChatRoomRespDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;

import java.util.List;

public interface ChatService {

    ChatSendRespDto processMessage(ChatSendReqDto chatSendReqDto);

    List<ChatRoomRespDto> queryChatRooms(Long memberId);

    List<ChatMessageRespDto> queryChatMessages(Long chatRoomId);
}
