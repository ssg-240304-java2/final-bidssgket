package com.ssg.bidssgket.user.domain.member.api.chat.controller;

import com.ssg.bidssgket.user.domain.member.api.chat.exception.MemberNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomMemberRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatMessageService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import jakarta.servlet.http.HttpSession;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @GetMapping
    public String getChatPage(Model model, HttpSession session) {
        // 사용자의 채팅방 목록 조회
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
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
    public String getMessagePage(@PathVariable Long chatRoomMemberNo, Model model, HttpSession session) {
        // 각 채팅방별 내용 불러오기
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();
        List<ChatRoomMember> chatRoomMembers = member.getChatRoomMembers();

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findById(chatRoomMemberNo).orElseThrow();
        ChatRoom chatRoom = chatRoomMember.getChatRoom();

        Long chatRoomNo = chatRoom.getChatRoomNo();
        Long memberNo = member.getMemberNo();
        String chatRoomName = chatRoom.getName();

        // 해당 채팅방의 메시지를 가져와서 DTO로 변환
        List<ChatMessageResDto> messages = chatMessageService.findMessagesByChatRoomNo(chatRoomNo)
                .stream()
                .map(ChatMessageResDto::fromEntity)
                .collect(Collectors.toList());

        model.addAttribute("chatRoomMembers", chatRoomMembers);
        model.addAttribute("chatRoomName", chatRoomName);
        model.addAttribute("chatRoomNo", chatRoomNo);
        model.addAttribute("messages", messages); // 메시지를 DTO로 변환하여 모델에 추가
        model.addAttribute("memberNo", memberNo);
        return "/user/member/chat";
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageResDto>> getChatMessages(@RequestParam Long chatRoomNo) {
        // 특정 채팅방의 메시지 목록 API
        List<ChatMessageResDto> messages = chatMessageService.findMessagesByChatRoomNo(chatRoomNo)
                .stream()
                .map(ChatMessageResDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public ResponseEntity<String> saveMessage(@RequestBody ChatMessageDto chatMessageDto, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();

        // 메시지 저장
        chatMessageService.createAndSaveChatMessage(chatMessageDto, member);
        return ResponseEntity.ok("메시지가 성공적으로 전송되었습니다.");
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/pro/{chatRoomNo}")
    public ChatMessageResDto handleMessage(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
        HttpSession session = (HttpSession) headerAccessor.getSessionAttributes().get("HTTP_SESSION");
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        Member member = memberRepository.findByEmail(sessionMember.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("Member not found for email: " + sessionMember.getEmail()));

        ChatMessage chatMessage = chatMessageService.createAndSaveChatMessage(chatMessageDto, member);
        ChatMessageResDto responseDto = ChatMessageResDto.fromEntity(chatMessage);
        messagingTemplate.convertAndSend("/pro/" + chatMessage.getChatRoom().getChatRoomNo(), responseDto);
        return responseDto;
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("member", chatMessage.getMember().getEmail());
        messagingTemplate.convertAndSend("/pro/" + chatMessage.getChatRoom().getChatRoomNo(), ChatMessageResDto.fromEntity(chatMessage));
    }
}