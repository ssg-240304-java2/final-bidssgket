package com.ssg.bidssgket.user.domain.product.api.dto.response;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductApiResDto {
    private Long productNo;
    private String productName;
    private String productDesc;
    private Boolean imdPurchase;
    private Boolean auctionSelected;
    private Boolean eventAuction;
    private Integer buyNowPrice;
    private Integer auctionStartPrice;
    private Integer bidSuccessPrice;
    private String category;
    private String salesStatus;

    public ProductApiResDto(Product product) {
        this.productNo = product.getProductNo();
        this.productName = product.getProductName();
        this.productDesc = product.getProductDesc();
        this.imdPurchase = product.getImdPurchase();
        this.auctionSelected = product.getAuctionSelected();
        this.eventAuction = product.getEventAuction();
        this.buyNowPrice = product.getBuyNowPrice();
        this.auctionStartPrice = product.getAuctionStartPrice();
        this.bidSuccessPrice = product.getBidSuccessPrice();
        this.category = product.getCategory().name();
        this.salesStatus = product.getSalesStatus().name();
    }
}
