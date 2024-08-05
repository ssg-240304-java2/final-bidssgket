package com.ssg.bidssgket.user.domain.product.view.dto.response;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResDto {
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
    private Integer bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;
    private List<String> imageUrls;

    public ProductResDto(Product product) {
        this.productNo = product.getProductNo();
        this.productName = product.getProductName();
        this.category = product.getCategory().name();
        this.productDesc = product.getProductDesc();
        this.salesStatus = product.getSalesStatus().name();
        this.imdPurchase = product.getImdPurchase();
        this.auctionSelected = product.getAuctionSelected();
        this.eventAuction = product.getEventAuction();
        this.buyNowPrice = product.getBuyNowPrice();
        this.auctionStartPrice = product.getAuctionStartPrice();
        this.bidSuccessPrice = product.getBidSuccessPrice();
        this.auctionStartTime = product.getAuctionStartTime();
        this.auctionEndTime = product.getAuctionEndTime();
        this.imageUrls = product.getProductImages().stream()
                .map(ProductImage::getProductImg)
                .collect(Collectors.toList());
    }
}
