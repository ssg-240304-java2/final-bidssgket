package com.ssg.bidssgket.user.domain.member.api.chat.controller;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.member.api.chat.exception.ChatRoomNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.exception.MemberNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomMemberRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatMessageService;
import com.ssg.bidssgket.user.domain.member.api.chat.service.ChatRoomService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.LineNumberGen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final ChatRoomRepository chatRoomRepository;
    private final ProductRepository productRepository;
    private final ChatRoomService chatRoomService;
    private final FileService fileService;

    @GetMapping
    public String getChatPage(Model model, HttpServletRequest request) {
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
    public String getMessagePage(@PathVariable Long chatRoomMemberNo, Model model, HttpServletRequest request) { // 각 채팅방별 내용 불러오기
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();
        List<ChatRoomMember> chatRoomMembers = member.getChatRoomMembers(); //현재 사용자가 속한 모든 채팅방 조회

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

    @PostMapping("/start")
    public String startChat(@RequestParam Long productNo, HttpServletRequest request) {
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");
        Member currentUser = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();

        // 상품 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productNo));

        // 기존 채팅방 조회
        ChatRoom existingChatRoom = chatRoomService.findByProductNo(productNo);

        if (existingChatRoom != null && existingChatRoom.getChatRoomMembers().equals(currentUser.getChatRoomMembers())) {
            // 이미 존재하는 채팅방으로 리디렉션
            return "redirect:/chat/" + existingChatRoom.getChatRoomNo();
        }

        // 채팅방 생성
        ChatRoom newChatRoom = chatRoomService.createRoom(productNo);

        // 판매자와 현재 사용자 채팅방에 추가
        Long sellerId = product.getMember().getMemberNo();
        Long currentUserId = currentUser.getMemberNo();

        chatRoomService.addMember(newChatRoom.getChatRoomNo(), sellerId);
        chatRoomService.addMember(newChatRoom.getChatRoomNo(), currentUserId);

        // 새로 생성된 채팅방으로 리디렉션
        return "redirect:/chat/" + newChatRoom.getChatRoomNo();
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
    public ChatMessageResDto handleMessage(@Payload ChatMessageDto chatMessageDto) {
        // 클라이언트로부터 받은 memberNo를 사용하여 Member 조회
        Long memberNo = chatMessageDto.getMemberNo();
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberNo));

        // 메시지 생성 및 저장
        ChatMessage chatMessage = chatMessageService.createAndSaveChatMessage(chatMessageDto, member);
        ChatMessageResDto responseDto = ChatMessageResDto.fromEntity(chatMessage);

        // 동적으로 채팅방 경로를 생성하여 메시지 전송
        messagingTemplate.convertAndSend("/pro/chatRoom/" + chatMessage.getChatRoom().getChatRoomNo(), responseDto);
        return responseDto;
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessageDto chatMessageDto) {
        // 클라이언트에서 보낸 memberNo를 기반으로 Member 조회
        Long memberNo = chatMessageDto.getMemberNo();
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberNo));

        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomNo())
                .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom not found"));

        // 사용자 추가 알림 메시지 생성
        ChatMessage chatMessage = ChatMessage.builder()
                .member(member)
                .chatRoom(chatRoom)
                .message("User " + member.getEmail() + " has joined the chat")
                .build();

        // 동적으로 채팅방 경로를 생성하여 메시지 전송
        messagingTemplate.convertAndSend("/pro/chatRoom/" + chatRoom.getChatRoomNo(), ChatMessageResDto.fromEntity(chatMessage));
    }

    @PostMapping("/upload")
    public ResponseEntity<ChatMessageResDto> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("chatRoomNo")Long chatRoomNo, HttpSession session) {
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();

        // 파일 업로드 처리
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        List<FileDto> uploadedFiles = fileService.uploadFiles(files, "chat/images");

        if (!uploadedFiles.isEmpty()) {
            String imageUrl = uploadedFiles.get(0).getUploadFileUrl();

            // ChatMessageDto 객체를 빌더 패턴으로 생성
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .memberNo(member.getMemberNo())
                    .chatRoomNo(chatRoomNo)
                    .imageUrl(imageUrl) // 이미지 URL을 메시지로 설정
                    .build();

            // 메시지 저장
            ChatMessage chatMessage = chatMessageService.createAndSaveChatMessage(chatMessageDto, member);
            ChatMessageResDto responseDto = ChatMessageResDto.fromEntity(chatMessage);

            // WebSocket을 통해 클라이언트에 메시지 전송
            messagingTemplate.convertAndSend("/pro/chatRoom/" + chatMessage.getChatRoom().getChatRoomNo(), responseDto);

            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}