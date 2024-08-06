package com.ssg.bidssgket.user.domain.auction.common;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuctionRegistDto {
    private Long productNo;
    private Integer auctionStartPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private String email;
}
