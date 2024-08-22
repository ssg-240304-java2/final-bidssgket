package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    public Wish(Member member,Product product) {
        this.member = member;
        this.product = product;
    }

    public static Wish createWish(Member member, Product product) {
        return Wish.builder()
                .member(member)
                .product(product)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setProduct(Product product) {this.product = product;}

}
