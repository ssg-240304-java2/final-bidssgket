package com.ssg.bidssgket.user.domain.productwish.domain.dto;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class WishDTO {

    private Long wishNo;
    private Member member;
    private Product product;

    @Builder
    public WishDTO(Long wishNo, Member member, Product product) {
        this.wishNo = wishNo;
        this.member = member;
        this.product = product;
    }
}
