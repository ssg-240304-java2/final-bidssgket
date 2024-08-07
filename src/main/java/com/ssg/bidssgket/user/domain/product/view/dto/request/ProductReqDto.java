package com.ssg.bidssgket.user.domain.product.view.dto.request;


import com.ssg.bidssgket.user.domain.product.domain.Sales_status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReqDto {
    private Long productNo;
    private String productName;
    private String category;
    private String productDesc;
    private Sales_status salesStatus;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private List<String> productImg;

    @Builder
    public ProductReqDto(Long productNo,String productName, String category, String productDesc, Sales_status salesStatus,
                         Boolean imdPurchase, Boolean auctionSelected, Boolean eventAuction,
                         Integer buyNowPrice, Integer auctionStartPrice, LocalDateTime auctionStartTime,
                         LocalDateTime auctionEndTime, List<String> productImg) {
        this.productNo = productNo;
        this.productName = productName;
        this.category = category;
        this.productDesc = productDesc;
        this.salesStatus = salesStatus;
        this.imdPurchase = imdPurchase;
        this.auctionSelected = auctionSelected;
        this.eventAuction = eventAuction;
        this.buyNowPrice = buyNowPrice;
        this.auctionStartPrice = auctionStartPrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.productImg = productImg;
    }

    public void setProductImg(List<String> productImg){
        this.productImg = productImg;
    }

    public Long getProductNo() {
        return productNo;
    }

    public void setProductNo(Long productNo) {
        this.productNo = productNo;
    }
}
