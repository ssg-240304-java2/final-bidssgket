package com.ssg.bidssgket.user.domain.member.api.chat.repository;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
