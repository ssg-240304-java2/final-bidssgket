package com.ssg.bidssgket.user.domain.auction.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AuctionReqDto {
    private int minTenderPrice;
    private int maxTenderPrice;
    private Long productNo;
}
