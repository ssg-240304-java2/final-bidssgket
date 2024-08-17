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
    @JoinColumn(name = "memberNo")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatImageNo")
    private ChatImage chatImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroomNo")
    private ChatRoom chatRoom;

    @Builder
    private ChatContent(String contents, Time time, Member member, ChatImage chatImage, ChatRoom chatRoom) {
        this.contents = contents;
        this.time = time;
        this.member = member;
        this.chatImage = chatImage;
        this.chatRoom = chatRoom;
    }

    public static ChatContent createChatContents(ChatContentDto chatContentDto, Member member, ChatImage chatImage, ChatRoom chatRoom) {
        return ChatContent.builder()
                .contents(chatContentDto.getContents())
                .time(chatContentDto.getTime())
                .member(member)
                .chatImage(chatImage)
                .chatRoom(chatRoom)
                .build();
    }

    public void setMember(Member nickname) {
        this.member = nickname;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setChatImage(ChatImage chatImage) {
        this.chatImage = chatImage;
    }


}
