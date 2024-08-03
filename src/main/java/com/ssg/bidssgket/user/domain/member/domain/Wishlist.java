package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public static WishList createWishList(Member member, Product product) {
        return WishList.builder()
                .member(member)
                .product(product)
                .build();
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

    public void setProductNo(Product product) {
        this.product = product;
    }

}
