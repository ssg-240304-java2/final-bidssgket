package com.ssg.bidssgket.user.domain.eventAuction.view.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidMessage {
    private Long memberNo;
    private int maxTenderPrice;
    private int minTenderPrice;
    private Long productNo;
    private String email;
}
