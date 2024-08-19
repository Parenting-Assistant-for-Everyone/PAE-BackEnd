package com.pae.server.chat.controller;

import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;
import com.pae.server.chat.service.ChatService;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅 메시지 전송
     */
    @MessageMapping("/chatroom")
    public void sendMessage(
            @RequestBody ChatSendReqDto chatSendReqDto
    ) {
        ChatSendRespDto response = chatService.processMessage(chatSendReqDto);

        messagingTemplate.convertAndSend(
                "/sub/chatroom/" + response.chatRoomId(),
                response
        );
    }

    /**
     * 채팅방 삭제
     */

    /**
     * 채팅방 목록 조회
     */

    /**
     * 채팅 목록 조회
     */


}
