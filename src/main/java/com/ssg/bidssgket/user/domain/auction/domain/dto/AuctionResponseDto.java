package com.ssg.bidssgket.user.domain.auction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponseDto {
    private Long bidNo;
    private int minTenderPrice;
    private int maxTenderPrice;
    private LocalDateTime tenderDate;
    private Boolean bidSuccess;
    private Boolean tenderDeleted;
    private String memberNickname;
    private String productName;
}
