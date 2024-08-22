package com.ssg.bidssgket.user.domain.member.api.chat.service;

import com.ssg.bidssgket.user.domain.member.api.chat.exception.ChatRoomNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.exception.MemberNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.exception.ProductNotFoundException;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoom;
import com.ssg.bidssgket.user.domain.member.api.chat.model.ChatRoomMember;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomMemberRepository;
import com.ssg.bidssgket.user.domain.member.api.chat.repository.ChatRoomRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public ChatRoom findByProductNo(Long productNo) {
        return chatRoomRepository.findByProductNo(productNo)
                .orElse(null); // 존재하지 않으면 null 반환
    }

    public ChatRoom createRoom(Long productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productNo));

        String chatRoomName = Optional.ofNullable(product.getProductName())
                .orElseThrow(() -> new IllegalArgumentException("Product name is required"));

        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomName)
                .product(product)
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    public void addMember(Long chatRoomNo, Long memberNo) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found"));

        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatRoom.addMember(chatRoomMember);
        chatRoomMemberRepository.save(chatRoomMember);
    }

    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    public Optional<ChatRoom> findRoomById(Long roomNo) {
        return chatRoomRepository.findById(roomNo);
    }
}
