package com.ssg.bidssgket.user.domain.productwish.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishDTO {

    private Long wishNo;
    private Long memberNo;
    private Long productNo;

    @Builder
    public WishDTO(Long wishNo, Long memberNo, Long productNo) {
        this.wishNo = wishNo;
        this.memberNo = memberNo;
        this.productNo = productNo;
    }
}
