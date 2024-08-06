package com.ssg.bidssgket.user.domain.auction.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionReqDto {
    private int minTenderPrice;
    private int maxTenderPrice;
    private Long productNo;
}
