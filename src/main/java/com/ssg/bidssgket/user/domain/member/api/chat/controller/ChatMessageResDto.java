package com.ssg.bidssgket.user.domain.member.api.chat.controller;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResDto {
    private Long memberNo;
    private String message;
    private String email;
    private String imageUrl;
    private LocalDateTime sentAt;

    public static ChatMessageResDto fromEntity(ChatMessage chatMessage) {
        ChatMessageResDto dto = new ChatMessageResDto();
        dto.memberNo = chatMessage.getMember().getMemberNo();
        dto.message = chatMessage.getMessage();
        dto.email = chatMessage.getMember().getEmail();
        dto.imageUrl = chatMessage.getImageUrl();
        dto.sentAt = chatMessage.getSentAt();
        return dto;
    }
}
