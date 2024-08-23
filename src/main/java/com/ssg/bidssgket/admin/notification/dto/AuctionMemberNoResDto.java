package com.ssg.bidssgket.admin.notification.dto;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionMemberNoResDto{
    private Long memberNo;

    public AuctionMemberNoResDto(Auction auction) {
        this.memberNo = auction.getMember().getMemberNo();
    }
}
