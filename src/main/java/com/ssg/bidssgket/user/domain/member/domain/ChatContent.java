package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.view.DTO.ChatContentDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatContentNo;
    private String contents;

    @Temporal(TemporalType.TIME)
    private Time time; // DateTimeEntity createdAt, updatedAt

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNickname")
    private Member nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatImageNo")
    private ChatImage chatImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroomNo")
    private ChatRoom chatRoom;

    @Builder
    private ChatContent(String contents, Time time, Member nickname, ChatImage chatImage, ChatRoom chatRoom) {
        this.contents = contents;
        this.time = time;
        this.nickname = nickname;
        this.chatImage = chatImage;
        this.chatRoom = chatRoom;
    }

    public static ChatContent createChatContents(ChatContentDto chatContentDto, Member member, ChatImage chatImage, ChatRoom chatRoom) {
        return ChatContent.builder()
                .contents(chatContentDto.getContents())
                .time(chatContentDto.getTime())
                .nickname(member)
                .chatImage(chatImage)
                .chatRoom(chatRoom)
                .build();
    }

    public void setNickname(Member nickname) {
        this.nickname = nickname;
    }

    public void setChatRoomNo(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setChatImageNo(ChatImage chatImage) {
        this.chatImage = chatImage;
    }


}
