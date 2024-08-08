package com.ssg.bidssgket.user.domain.product.view.dto.request;


import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
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
    private SalesStatus salesStatus;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private LocalDateTime auctionEndTime;
    private List<ProductImage> productImages;

    @Builder
    public ProductReqDto(Long productNo, String productName, String category, String productDesc, SalesStatus salesStatus,
                         Boolean imdPurchase, Boolean auctionSelected, Boolean eventAuction,
                         Integer buyNowPrice, Integer auctionStartPrice, LocalDateTime auctionEndTime,
                         List<ProductImage> productImg) {
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
        this.auctionEndTime = auctionEndTime;
        this.productImages = productImg;
    }


    public Long getProductNo() {
        return productNo;
    }

    public void setProductNo(Long productNo) {
        this.productNo = productNo;
    }
}
