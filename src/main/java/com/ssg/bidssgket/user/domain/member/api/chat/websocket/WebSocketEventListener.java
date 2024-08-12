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
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) { //WebSocket 연결이 해제될 때 호출
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        if(member != null) {
            logger.info("User Disconnected : " + member);

            ChatMessage chatMessage = ChatMessage.builder()
                    .member(member)
                    .message("User Disconnected")
                    .build();

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}