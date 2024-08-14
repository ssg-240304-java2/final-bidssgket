package com.ssg.bidssgket.user.domain.member.view.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishDto {

    private Long wishNo;
    private String member;
    private String product;

    @Builder
    public WishDto(Long wishNo, String member, String product) {
        this.wishNo = wishNo;
        this.member = member;
        this.product = product;
    }
}
