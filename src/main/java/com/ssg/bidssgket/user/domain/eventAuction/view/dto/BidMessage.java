package com.ssg.bidssgket.user.domain.eventAuction.view.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidMessage {
    private Long bidNo;
    private int maxTenderPrice;
}
