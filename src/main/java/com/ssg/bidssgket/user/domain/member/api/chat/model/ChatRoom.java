package com.ssg.bidssgket.user.domain.member.api.chat.model;

import com.ssg.bidssgket.user.domain.member.view.DTO.ChatRoomDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"chatRoomNo", "name"})
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_no")
    private Long chatRoomNo;

    private String name; // 채팅방 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @OneToMany(mappedBy = "chatRoom", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    @Builder
    public ChatRoom(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    public void addMember(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.add(chatRoomMember);
    }

    public void addMessage(ChatMessage message) {
        this.messages.add(message);
    }

    public static ChatRoom createChatRoom(ChatRoomDto chatRoomDto, Product product) {
        return ChatRoom.builder()
                .name(chatRoomDto.getName())
                .product(product)
                .build();
    }
}
