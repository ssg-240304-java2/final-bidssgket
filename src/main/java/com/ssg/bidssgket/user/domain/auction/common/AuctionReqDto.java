package com.ssg.bidssgket.user.domain.auction.common;

import lombok.Data;
import org.joda.time.DateTime;

@Data

public class AuctionReqDto {
    private int bidNo;
    private int minTenderPrice;
    private int maxTenderPrice;
    private DateTime tenderDate;
    private Boolean bidSuccess;
    private Boolean tenderDeleted;
    /*private int userNo;
    private int productNo;*/
}
