package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.One;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private List<Product> products = new ArrayList<>();

    @Builder
    public WishList(Member member, List<Product> products) {
        this.member = member;
        this.products = products;
    }

    public static WishList createWishList(Member member, List<Product> products) {
        return WishList.builder()
                .member(member)
                .products(products)
                .build();
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

    public void setWishListNo(List<Product> products) {
        this.products = products;
    }

}
