package com.ssg.bidssgket.user.domain.product.view.dto.response;


import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponseDto {
    private Long productNo;
    private String productName;
    private String category;
    private String productDesc;
    private String salesStatus;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private List<ProductImage> productImages;
    private Integer bidSuccessPrice;

    @Builder
    public ProductResponseDto(Long productNo, String productName, String category, String productDesc, String salesStatus, Boolean imdPurchase, Boolean auctionSelected, Boolean eventAuction, Integer buyNowPrice, Integer auctionStartPrice, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, List<ProductImage> productImages, Integer bidSuccessPrice) {
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
        this.productImages = productImages;
        this.bidSuccessPrice = bidSuccessPrice;
    }



    @Override
    public String toString() {
        return "ProductResDto{" +
                "productNo=" + productNo +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", salesStatus='" + salesStatus + '\'' +
                ", imdPurchase=" + imdPurchase +
                ", auctionSelected=" + auctionSelected +
                ", eventAuction=" + eventAuction +
                ", buyNowPrice=" + buyNowPrice +
                ", auctionStartPrice=" + auctionStartPrice +
                ", auctionStartTime=" + auctionStartTime +
                ", auctionEndTime=" + auctionEndTime +
                ", productImages=" + productImages +
                ", bidSuccessPrice=" + bidSuccessPrice +
                '}';
    }
}
