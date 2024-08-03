package com.ssg.bidssgket.user.domain.auction.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionReqDto {
    private Long bidNo;
    private int minTenderPrice;
    private int maxTenderPrice;
    private DateTime tenderDate;
    private Boolean bidSuccess;
    private Boolean tenderDeleted;
}
