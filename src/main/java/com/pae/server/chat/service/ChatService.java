package com.pae.server.chat.service;

import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;

public interface ChatService {

    ChatSendRespDto processMessage(ChatSendReqDto chatSendReqDto);
}
