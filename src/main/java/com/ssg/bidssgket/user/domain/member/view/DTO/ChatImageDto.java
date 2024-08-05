package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.ChatContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatImageDto {
    private Long chatImageNo;
    private String chatImage;
//    private ChatContent chatContent;

}
