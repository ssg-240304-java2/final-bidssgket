package com.ssg.bidssgket.user.domain.member.api.chat.repository;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom_ChatRoomNoOrderBySentAtAsc(Long chatRoomNo);
}
