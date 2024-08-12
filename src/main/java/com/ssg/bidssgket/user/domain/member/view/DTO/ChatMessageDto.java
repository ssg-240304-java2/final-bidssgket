package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageDto {
    private Long chatMessageNo;
    private Long chatRoomNo;
    private String MemberNickname;
    private Long memberNo;
    private String message;
    private String imageUrl;
    private LocalDateTime sentAt;

    @Builder
    public ChatMessageDto(Long chatMessageNo, Long chatRoomNo, String MemberNickname, Long memberNo, String message, String imageUrl, LocalDateTime sentAt) {
        this.chatMessageNo = chatMessageNo;
        this.chatRoomNo = chatRoomNo;
        this.MemberNickname = MemberNickname;
        this.memberNo = memberNo;
        this.message = message;
        this.imageUrl = imageUrl;
        this.sentAt = sentAt;
    }
}
