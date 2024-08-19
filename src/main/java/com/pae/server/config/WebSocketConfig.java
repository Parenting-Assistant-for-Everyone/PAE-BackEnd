package com.pae.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url = ws://localhost:8080/ws, 프로토콜이 http가 아니다!
        // ws://localhost/ws 를 호출하면 websocket이 연결된다.
        registry.addEndpoint("/connection/ws")
                .setAllowedOrigins("*")
                .withSockJS();
        registry.addEndpoint("/connection/ws")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메모리 기반 메시지 브로커가 구독하고 있는 클라에게 메시지 전송
        // 추후 다중 인스턴스 환경에서는 kafka, rabbibMQ같은 메시지 브로커를 사용할 수 있다.

        // 메시지를 구독(수신)하는 요청 엔드포인트 - 채팅방에 구독 신청
        // 클라단에서 서버단으로 요청시 수신 이라면 /sub 으로 url 의 끝부분에 작성
        registry.enableSimpleBroker("/sub");

        // 메시지를 전송(송신)하는 엔드포인트 - 구독한 채팅방에 메시지 전송
        // 클라단에서 서버단으로 요청시 송신 이라면 /pub 으로 url 의 끝부분에 작성
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
