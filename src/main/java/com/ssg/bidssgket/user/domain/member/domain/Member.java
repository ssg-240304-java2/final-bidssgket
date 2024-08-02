package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;
    private String memberName;
    private String id;
    private String pwd;

    private String phone;
    private String provider;
    private String providerId;
    private Integer biscuit;

    @Embedded
    private Address address;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("false")
    private boolean isPenalty;

    @OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Wishlist> wishlists=new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Review> reviews = new ArrayList<>();


    private String nickName;


    @Builder
    private Member(String memberName, String id, String pwd, String nickName, String phone, String provider, String providerId, Integer biscuit, Address address) {
        this.memberName = memberName;
        this.id = id;
        this.pwd = pwd;
        this.nickName = nickName;
        this.phone = phone;
        this.provider = provider;
        this.providerId = providerId;
        this.biscuit = biscuit;
        this.address = address;
    }

//    public static Member member(MemberDto memberDto){
//    }

    public void addWishList(Wishlist wishlist){
        if(wishlist.getMember()!=null){
            wishlist.getMember().getWishlists().remove(wishlist);
        }
        wishlist.setMember(this);
        this.wishlists.add(wishlist);
    }

    public void addReview(Review review){
        if (review.getMember()!=null){
            review.getMember().getReviews().remove(review);
        }
        review.setMember(this);
        this.reviews.add(review);
    }
}
