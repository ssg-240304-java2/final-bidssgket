package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo; // 사용자 고유번호
    private String memberName; // 사용자 이름
    private String id; // 사용자 아이디
    private String pwd; // 사용자 비밀번호
    private String phone; // 핸드폰 번호
    private String memberNickname;
//    private String provider;
//    private String providerId;
    private Integer biscuit; // 비스킷 온도
    private boolean isDeleted; // 탈퇴 여부
    private boolean isPenalty; // 패널티 여부

    @Embedded
    private Address address; // 주소

    @PrePersist
    protected void onCreate() {
        if (this.biscuit == null) {
            this.biscuit = 50;
        }
        if (!this.isDeleted) {
            this.isDeleted = false;
        }
        if (!this.isPenalty) {
            this.isPenalty = false;
        }
    }

    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @OneToOne(mappedBy = "reviewer", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private Review reviewer;

    @OneToOne(mappedBy = "reviewee", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private Review reviewee;

    @OneToOne(mappedBy = "sender", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private ChatRoom sender;

    @OneToOne(mappedBy = "receiver", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private ChatRoom receiver;

    @OneToOne(mappedBy = "member", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private ChatContent chatContent;


    @Builder
    private Member(String memberName, String id, String pwd, String memberNickname, String phone,Integer biscuit, Address address,Boolean isDeleted,Boolean isPenalty) {
        this.memberName = memberName;
        this.id = id;
        this.pwd = pwd;
        this.memberNickname= memberNickname;
        this.phone = phone;
        this.biscuit = biscuit;
        this.address = address;
        this.isDeleted = isDeleted;
        this.isPenalty = isPenalty;
    }

    public static Member createMember(MemberDto memberDto) {
        return Member.builder()
                .memberName(memberDto.getMemberName())
                .id(memberDto.getId())
                .pwd(memberDto.getPwd())
                .memberNickname(memberDto.getMemberNickname())
                .phone(memberDto.getPhone())
                .biscuit(memberDto.getBiscuit())
                .address(memberDto.getAddress())
                .isDeleted(memberDto.getIsDeleted())
                .isPenalty(memberDto.getIsPenalty())
                .build();
    }

    /**
     * 양방향 연관관계, cascade 유의
     */

    public void addWishList(WishList wishList) {
        if(wishList.getMember() != null){
            wishList.getMember().getWishLists().remove(wishList);
        }
        wishList.setMember(this);
        this.wishLists.add(wishList);
    }

    public void addReviewer(Review reviewer){
        reviewer.setReviewer(this);
        this.reviewer = reviewer;
    }

    public void addReviewee(Review reviewee){
        reviewee.setReviewee(this);
        this.reviewee = reviewee;
    }

    public void addSender(ChatRoom sender){
        sender.setSender(this);
        this.sender = sender;
    }

    public void addReceiver(ChatRoom receiver){
        receiver.setReceiver(this);
        this.receiver = receiver;
    }

    public void addNickname(ChatContent chatContent){
        chatContent.setMember(this);
        this.chatContent = chatContent;
    }

}
