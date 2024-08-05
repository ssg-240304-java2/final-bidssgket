package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.ChatContent;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {
    private Long chatroomNo;
    private Member sender;
    private Member receiver;
    private List<ChatContent> chatContents;
}
