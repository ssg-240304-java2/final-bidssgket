package com.ssg.bidssgket.user.domain.member.api.chat.websocket;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) { // WebSocket 연결이 생성될 때 호출
        logger.info("Received a new web socket connection");
        // 웹소켓 연결 시 사용자 정보를 세션에 저장.
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        if (member != null) {
            headerAccessor.getSessionAttributes().put("username", member.getMemberNickname()); // 사용자 이름을 저장
            logger.info("User Connected : " + member.getMemberNickname());
            // 사용자가 연결될 때 알림 메시지를 보내는 경우
            ChatMessage chatMessage = ChatMessage.builder()
                    .message(member.getMemberNickname() + "님이 연결되었습니다.")
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) { //WebSocket 연결이 해제될 때 호출
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        if(member != null) {
            logger.info("User Disconnected : " + member);

            ChatMessage chatMessage = ChatMessage.builder()
                    .message(member.getMemberNickname()+"님이 나갔습니다.")
                    .build();

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}