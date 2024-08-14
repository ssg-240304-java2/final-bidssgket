package com.ssg.bidssgket.user.domain.member.api.chat.Controller;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomMemberRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatMessageService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @GetMapping
    public String getChatPage(Model model, HttpServletRequest request) { // 사용자의 채팅목록 전부 불러옴

        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();

        List<ChatRoomMember> chatRoomMembers = member.getChatRoomMembers();
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRoomMembers.forEach(chatRoomMember -> chatRooms.add(chatRoomMember.getChatRoom()));

        model.addAttribute("chatRoomMembers", chatRoomMembers);
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("memberNo", member.getMemberNo());
        return "/user/member/chat";
    }

    @GetMapping("/{chatRoomMemberNo}")
    public String getMessagePage(@PathVariable Long chatRoomMemberNo, Model model, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();
        List<ChatRoomMember> chatRoomMembers = member.getChatRoomMembers();
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findById(chatRoomMemberNo).orElseThrow();
        ChatRoom chatRoom = chatRoomMember.getChatRoom();

        Long chatRoomNo = chatRoom.getChatRoomNo();
        List<ChatMessage> messages = chatMessageService.findMessagesByChatRoomNo(chatRoomNo);
        Long memberNo = member.getMemberNo();

        String chatRoomName = chatRoom.getName();

        model.addAttribute("chatRoomMembers", chatRoomMembers);
        model.addAttribute("chatRoomName", chatRoomName);
        model.addAttribute("chatRoomNo", chatRoomNo);
        model.addAttribute("messages", messages);
        model.addAttribute("memberNo", memberNo);
        return "/user/member/chat";
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@RequestParam Long chatRoomNo) { // 특정 채팅방의 메세지 목록을 불러옴
        List<ChatMessage> messages = chatMessageService.findMessagesByChatRoomNo(chatRoomNo);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public String saveMessage(@RequestParam Long chatRoomNo,
                              @RequestParam Long memberNo,
                              @RequestParam String message,
                              @RequestParam(required = false) String imageUrl,
                              Model model) {   // 전송된 메세지 저장

        // 채팅방과 회원 정보를 데이터베이스에서 조회
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(chatRoomNo);
        Optional<Member> memberOpt = memberRepository.findById(memberNo);

        if (chatRoomOpt.isEmpty() || memberOpt.isEmpty()) {
            model.addAttribute("error", "채팅방이나 회원 정보가 올바르지 않습니다.");
            return "error"; // 에러 페이지로 리디렉션
        }

        ChatRoom chatRoom = chatRoomOpt.get();
        Member member = memberOpt.get();

        // ChatMessage 객체 생성
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .member(member)
                .message(message)
                .imageUrl(imageUrl)
                .sentAt(LocalDateTime.now())
                .build();

        // 채팅 메시지 저장
        chatMessageService.save(chatMessage);

        // 성공적인 응답 반환
        model.addAttribute("successMessage", "메시지가 성공적으로 전송되었습니다.");
        return "redirect:/chat"; // 채팅 페이지로 리디렉션
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/{chatRoomNo}")
    public ChatMessage handleMessage(@Payload ChatMessageDto chatMessageDto) {  // 메세지 전송
        ChatMessage chatMessage = chatMessageService.createAndSaveChatMessage(chatMessageDto);
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChatRoom().getChatRoomNo(), chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // WebSocket 세션에 사용자 이름 저장
        headerAccessor.getSessionAttributes().put("member", chatMessage.getSender());
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChatRoom().getChatRoomNo(), chatMessage);
    }

}
