package com.ssg.bidssgket.user.domain.member.api.chat.Controller;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatMessageService;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("chat")
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;


    @GetMapping
    public String getChatPage() {
        return "/user/member/chatStart";
    }

    @PostMapping("/rooms")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam Long productNo) {  //채팅방생성
        return chatRoomService.createRoom(productNo);
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> findAllRooms() { //모든 채팅방 조회
        return chatRoomService.findAllRooms();
    }

    @PostMapping("/rooms/{roomNo}/members")
    @ResponseBody
    public void addMemberToRoom(@PathVariable Long roomNo, @RequestParam Long memberNo) {
        chatRoomService.addMember(roomNo, memberNo);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessageService.save(chatMessage);
    }
}
