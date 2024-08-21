package com.pae.server.chat.controller;

import com.pae.server.chat.dto.request.ChatRoomCreateReqDto;
import com.pae.server.chat.dto.request.ChatSendReqDto;
import com.pae.server.chat.dto.response.ChatMessageRespDto;
import com.pae.server.chat.dto.response.ChatRoomCreateRespDto;
import com.pae.server.chat.dto.response.ChatRoomRespDto;
import com.pae.server.chat.dto.response.ChatSendRespDto;
import com.pae.server.chat.service.ChatService;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /***
     * 채팅방 생성
     */
    @PostMapping("/api/v1/member/chatRoom")
    public ResponseEntity<ApiResponse<ChatRoomCreateRespDto>> createChatRoom(
            @RequestBody ChatRoomCreateReqDto chatRoomCreateReqDto
    ) {
        ChatRoomCreateRespDto response = chatService.createChatRoom(chatRoomCreateReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /**
     * 채팅 메시지 전송
     */
    @MessageMapping("/chatroom/{chatRoomId}")
    public void sendMessage(
            @DestinationVariable Long chatRoomId,
            @RequestBody ChatSendReqDto chatSendReqDto
    ) {
        ChatSendRespDto response = chatService.sendMessage(chatSendReqDto, chatRoomId);

        messagingTemplate.convertAndSend(
                "/sub/chatroom/" + chatRoomId,
                response
        );
    }

    /**
     * 채팅방 목록 조회
     */
    @GetMapping("/api/v1/member/chatrooms/{memberId}")
    public ResponseEntity<ApiResponse<List<ChatRoomRespDto>>> queryChatRooms(
            @PathVariable Long memberId // Todo : 추후 @Authentication... 애너테이션으로 변경해야함
    ) {
        List<ChatRoomRespDto> response = chatService.queryChatRooms(memberId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /**
     * 채팅 목록 조회
     */
    @GetMapping("/api/v1/member/chat/{chatRoomId}")
    public ResponseEntity<ApiResponse<List<ChatMessageRespDto>>> queryChatMessages(
            @PathVariable Long chatRoomId
    ) {
        List<ChatMessageRespDto> response = chatService.queryChatMessages(chatRoomId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    /**
     * 채팅방 삭제
     */

}
