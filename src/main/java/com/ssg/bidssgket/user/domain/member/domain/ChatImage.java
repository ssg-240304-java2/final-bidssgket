package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.view.DTO.ChatImageDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ChatImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatImageNo;

    @Column
    private String chatImage;

    @OneToOne(mappedBy = "chatImage",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private ChatContent chatContent;

    @Builder
    private ChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public static ChatImage createChatImage(ChatImageDto chatImageDto) {
        return ChatImage.builder()
                .chatImage(chatImageDto.getChatImage())
                .build();
    }

    public void addChatContent(ChatContent chatContent) {
        chatContent.setChatImage(this);
        this.chatContent = chatContent;
    }

}
