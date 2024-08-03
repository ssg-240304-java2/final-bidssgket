package com.ssg.bidssgket.user.domain.auction.domain;

import com.ssg.bidssgket.user.domain.auction.common.AuctionReqDto;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;


@Entity
@Table(name = "auction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidNo;
    private int minTenderPrice;
    private int maxTenderPrice;
    private DateTime tenderDate;
    private Boolean bidSuccess;
    private Boolean tenderDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productNo")
    private Product product;

    @Builder
    private Auction(int minTenderPrice, int maxTenderPrice, DateTime tenderDate, Boolean bidSuccess, Boolean tenderDeleted, Member member, Product product) {
        this.minTenderPrice = minTenderPrice;
        this.maxTenderPrice = maxTenderPrice;
        this.tenderDate = tenderDate;
        this.bidSuccess = bidSuccess;
        this.tenderDeleted = tenderDeleted;
        this.member = member;
        this.product = product;
    }


    public static Auction createAuction(AuctionReqDto auctionDto, Member member, Product product){
        return Auction.builder()
                .minTenderPrice(auctionDto.getMinTenderPrice())
                .maxTenderPrice(auctionDto.getMaxTenderPrice())
                .tenderDate(auctionDto.getTenderDate())
                .bidSuccess(auctionDto.getBidSuccess())
                .tenderDeleted(auctionDto.getTenderDeleted())
                .member(member)
                .product(product)
                .build();
    }
    public void setProduct(Product product){
        this.product = product;
    }
}
