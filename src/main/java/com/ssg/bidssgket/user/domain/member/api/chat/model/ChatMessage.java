package com.ssg.bidssgket.user.domain.member.api.chat.model;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.view.DTO.ChatMessageDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageNo;

    @ManyToOne
    @JoinColumn(name = "chat_room_no")
    private ChatRoom chatRoom;

    private String MemberNickname; // 채팅방에서 사용할 사용자 이름

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    private String message;

    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime sentAt;

    @Builder
    public ChatMessage(ChatRoom chatRoom, String MemberNickname, Member member, String message, String imageUrl, LocalDateTime sentAt) {
        this.chatRoom = chatRoom;
        this.MemberNickname = MemberNickname;
        this.member = member;
        this.message = message;
        this.imageUrl = imageUrl;
        this.sentAt = sentAt == null ? LocalDateTime.now() : sentAt;
    }

    public void setMember(Member member) {this.member = member;}

    public static ChatMessage createChatMessage(ChatMessageDto chatMessageDto, ChatRoom chatRoom, Member member) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .MemberNickname(chatMessageDto.getMemberNickname())
                .member(member)
                .message(chatMessageDto.getMessage())
                .imageUrl(chatMessageDto.getImageUrl())
                .sentAt(chatMessageDto.getSentAt())
                .build();
    }
}
