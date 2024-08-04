package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

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

    /**
     * Product랑 WishList N:M 같은데 확인 부탁드립니다.
     */
    @OneToMany(mappedBy = "wishList", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Builder
    public WishList(Member member) {
        this.member = member;
    }

    public static WishList createWishList(Member member, List<Product> products) {
        return WishList.builder()
                .member(member)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void addProduct(Product product) {
        if(product.getWishList() != null){
            product.getWishList().getProducts().remove(product);
        }

        product.setWishList(this);
        this.products.add(product);
    }


}
