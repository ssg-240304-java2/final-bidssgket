package com.ssg.bidssgket.user.domain.member.api.chat.service;

import com.ssg.bidssgket.user.domain.member.api.chat.exception.ChatRoomNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.exception.MemberNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatMessageRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final HttpSession session;

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findMessagesByChatRoomNo(Long chatRoomNo) {
        return chatMessageRepository.findByChatRoom_ChatRoomNoOrderBySentAtAsc(chatRoomNo);
    }

    public ChatMessage createAndSaveChatMessage(ChatMessageDto chatMessageDto) {
        Long chatRoomNo = chatMessageDto.getChatRoomNo();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new MemberNotFoundException("로그인된 사용자가 없습니다.");
        }


        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found"));

        ChatMessage chatMessage = ChatMessage.createChatMessage(chatMessageDto, chatRoom, member);
        chatRoom.addMessage(chatMessage);

        return chatMessageRepository.save(chatMessage);
    }


}
