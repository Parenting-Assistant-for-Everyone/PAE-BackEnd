package com.pae.server.chat.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatEventListener {
    // 현재 접속 중인 유저 정보 저장 (sessionId를 키로 사용)
    private final Map<String, Long> sessionIdToUserMap = new ConcurrentHashMap<>(); // userId로 수정
    private final Map<Long, Set<Long>> roomIdToUsersMap = new ConcurrentHashMap<>(); // userId로 수정

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("웹소켓 연결");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        log.info("세션아이디 : {}", sessionId);

        // CONNECT 메시지에서 헤더 정보 추출
        MessageHeaders headers = accessor.getMessageHeaders();
        StompHeaderAccessor connectAccessor = StompHeaderAccessor.wrap((org.springframework.messaging.Message<?>) headers.get("simpConnectMessage"));

        String userIdString = connectAccessor.getFirstNativeHeader("userId"); // userId 가져오기
        String roomId = connectAccessor.getFirstNativeHeader("roomId");

        if (userIdString != null && roomId != null) {
            Long userId = Long.parseLong(userIdString); // Long 타입으로 변환
            log.info("입장한 유저 ID : {}", userId);
            log.info("입장한 방 id : {}", roomId);

            sessionIdToUserMap.put(sessionId, userId);
            roomIdToUsersMap.computeIfAbsent(Long.parseLong(roomId), k -> ConcurrentHashMap.newKeySet()).add(userId);
        } else {
            log.warn("userId 또는 roomId가 누락되었습니다.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("웹소켓 해제");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        log.info("세션아이디 : {}", sessionId);

        Long userId = sessionIdToUserMap.get(sessionId);
        log.info("퇴장한 유저 ID : {}", userId);

        if (userId != null) {
            sessionIdToUserMap.remove(sessionId);
            roomIdToUsersMap.forEach((roomId, users) -> users.remove(userId));
        }
    }

    public boolean isUserInRoom(Long roomId) {
        Set<Long> userIds = roomIdToUsersMap.get(roomId);
        return userIds != null && userIds.size() == 2;
    }
}
