package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {
    private Long chatRoomNo;
    private String name;
    private Product product;
    private List<ChatRoomMemberDto> chatRoomMembers;
    private List<ChatMessageDto> chatMessages;

    @Builder
    public ChatRoomDto(Long chatRoomNo, String name, Product product, List<ChatRoomMemberDto> chatRoomMembers, List<ChatMessageDto> chatMessages) {
        this.chatRoomNo = chatRoomNo;
        this.name = name;
        this.product = product;
        this.chatRoomMembers = chatRoomMembers;
        this.chatMessages = chatMessages;
    }
}
