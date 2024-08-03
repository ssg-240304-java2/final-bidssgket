package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.view.DTO.ChatRoomDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderNo")
    private Member sender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverNo")
    private Member receiver;

    @OneToMany(mappedBy = "chatRoom",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<ChatContent> chatContents = new ArrayList<>();

    @Builder
    private ChatRoom(Long chatroomNo, Member sender, Member receiver, List<ChatContent> chatContents) {
        this.chatroomNo = chatroomNo;
        this.sender = sender;
        this.receiver = receiver;
    }

    public static ChatRoom createChatRoom(ChatRoomDto chatRoomDto){
        return ChatRoom.builder()
                .sender(chatRoomDto.getSender())
                .receiver(chatRoomDto.getReceiver())
                .chatContents(chatRoomDto.getChatContents())
                .build();

    }

    public void setSenderNo(Member sender) {
        this.sender = sender;
    }

    public void setReceiverNo(Member receiver) {
        this.receiver = receiver;
    }

    public void addchatContents(ChatContent chatContent) {
        if(chatContent.getChatRoom() != null){
            chatContent.getChatRoom().getChatContents().remove(chatContent);
        }
        chatContent.setChatRoomNo(this);
        this.chatContents.add(chatContent);
    }

}
