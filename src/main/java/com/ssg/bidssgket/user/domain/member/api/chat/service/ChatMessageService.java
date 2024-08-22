package com.ssg.bidssgket.user.domain.member.api.chat.service;

import com.ssg.bidssgket.user.domain.member.api.chat.exception.ChatRoomNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatMessageRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findMessagesByChatRoomNo(Long chatRoomNo) {
        return chatMessageRepository.findByChatRoom_ChatRoomNoOrderBySentAtAsc(chatRoomNo);
    }

    public ChatMessage createAndSaveChatMessage(ChatMessageDto chatMessageDto, Member member) {
        Long chatRoomNo = chatMessageDto.getChatRoomNo();

        // 채팅방 번호로 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));

        // ChatMessage 객체 생성
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatMessageDto, chatRoom, member);

        // 채팅방에 메시지 추가
        chatRoom.addMessage(chatMessage);

        // 생성된 메시지를 저장하고 반환
        return chatMessageRepository.save(chatMessage);
    }
}