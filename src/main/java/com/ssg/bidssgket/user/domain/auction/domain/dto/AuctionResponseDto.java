package com.ssg.bidssgket.user.domain.auction.domain.dto;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionResponseDto {
    private Long bidNo;
    private int minTenderPrice;
    private int maxTenderPrice;
    private LocalDateTime tenderDate;
    private Boolean bidSuccess;
    private Boolean tenderDeleted;
    private Member member;
    private Product product;

    @Builder
    public AuctionResponseDto(Long bidNo, int minTenderPrice, int maxTenderPrice, LocalDateTime tenderDate, Boolean bidSuccess, Boolean tenderDeleted, Member member, Product product) {
        this.bidNo = bidNo;
        this.minTenderPrice = minTenderPrice;
        this.maxTenderPrice = maxTenderPrice;
        this.tenderDate = tenderDate;
        this.bidSuccess = bidSuccess;
        this.tenderDeleted = tenderDeleted;
        this.member = member;
        this.product = product;
    }
}
