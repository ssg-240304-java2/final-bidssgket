package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishListDto {
    private Long wishProductNo;
    private Member member;
    private Product product;
}
