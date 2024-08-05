package com.ssg.bidssgket.user.domain.product.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistProductReqDto {
    private String productName;
    private String category;
    private String productDesc;
    private String salesStatus;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private Integer bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public void setImdPurchase(Boolean imdPurchase) {
        this.imdPurchase = imdPurchase;
    }

    public void setAuctionSelected(Boolean auctionSelected) {
        this.auctionSelected = auctionSelected;
    }

    public void setEventAuction(Boolean eventAuction) {
        this.eventAuction = eventAuction;
    }

    public void setBuyNowPrice(Integer buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public void setAuctionStartPrice(Integer auctionStartPrice) {
        this.auctionStartPrice = auctionStartPrice;
    }

    public void setBidSuccessPrice(Integer bidSuccessPrice) {
        this.bidSuccessPrice = bidSuccessPrice;
    }

    public void setAuctionStartTime(LocalDateTime auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }
}
