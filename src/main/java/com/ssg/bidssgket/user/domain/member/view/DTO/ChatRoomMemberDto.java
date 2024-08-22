package com.ssg.bidssgket.user.domain.member.view.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMemberDto {

    private Long chatRoomMemberNo;
    private Long chatRoomNo;
    private Long memberNo;

    @Builder
    public ChatRoomMemberDto(Long chatRoomMemberNo, Long chatRoomNo, Long memberNo) {
        this.chatRoomMemberNo = chatRoomMemberNo;
        this.chatRoomNo = chatRoomNo;
        this.memberNo = memberNo;
    }
}
