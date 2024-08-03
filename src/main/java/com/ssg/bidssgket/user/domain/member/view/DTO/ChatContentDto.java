package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.ChatImage;
import com.ssg.bidssgket.user.domain.member.domain.ChatRoom;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatContentDto {
    private Long chatContentsNo;
    private String contents;
    private Time time;
    private Member nickname;
    private ChatImage chatImage;
    private ChatRoom chatRoom;
}
