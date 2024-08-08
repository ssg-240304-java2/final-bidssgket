//package com.ssg.bidssgket.user.domain.member.api.chat.Controller;
//
//import com.ssg.bidssgket.user.domain.member.api.chat.Dto.ChatRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatController {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    //입장을 할 때 사용하는 루트입니다.
//    @MessageMapping("/chat/enter")
//    public void enter(@RequestBody ChatRequestDto dto) {
//        messagingTemplate.convertAndSend("/sub/chat/room/1", dto);
//    }
//
//    //메세지를 전송할 때 사용하는 루트입니다.
//    @MessageMapping("/chat/message")
//    public void message(@RequestBody ChatRequestDto dto) {
//        messagingTemplate.convertAndSend("/sub/chat/room/1", dto);
//    }
//}
