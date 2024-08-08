package com.ssg.bidssgket.user.domain.product.view.dto.response;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResDto {
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
    private List<String> productImages;

    @Builder
    public ProductResDto(Product product) {
        this.productName = product.getProductName();
        this.category = product.getCategory().name();
        this.productDesc = product.getProductDesc();
        this.salesStatus = product.getSalesStatus().name();
        this.imdPurchase = product.getImdPurchase();
        this.auctionSelected = product.getAuctionSelected();
        this.eventAuction = product.getEventAuction();
        this.buyNowPrice = product.getBuyNowPrice();
        this.auctionStartPrice = product.getAuctionStartPrice();
        this.auctionStartTime = product.getAuctionStartTime();
        this.auctionEndTime = product.getAuctionEndTime();
        this.productImages = product.getProductImages().stream()
                .map(ProductImage::getProductImg)
                .collect(Collectors.toList());
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
