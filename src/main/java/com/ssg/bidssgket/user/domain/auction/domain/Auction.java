package com.ssg.bidssgket.user.domain.auction.domain;

import com.ssg.bidssgket.user.domain.auction.common.AuctionDto;
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
    private int bidNo;
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
    private Auction(int minTenderPrice, int maxTenderPrice, DateTime tenderDate, Boolean bidSuccess, Boolean tenderDeleted) {
        this.minTenderPrice = minTenderPrice;
        this.maxTenderPrice = maxTenderPrice;
        this.tenderDate = tenderDate;
        this.bidSuccess = bidSuccess;
        this.tenderDeleted = tenderDeleted;
    }

/*    public static Auction createAuction(AuctionDto auctionDto, Member member, Product product){
        return Auction.builder()
    }

    public void setProduct(Product product){
        this.product = product;
    }*/





}
