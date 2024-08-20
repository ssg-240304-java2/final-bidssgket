package com.ssg.bidssgket.user.domain.member.api.chat.repository;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
