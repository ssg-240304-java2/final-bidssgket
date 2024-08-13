package com.ssg.bidssgket.admin.api.product.product.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminProductResDto {
    private Long productNo;
    private String productName;
    private Category category;
    private SalesStatus salesStatus;
    private String imdPurchase;
    private Integer buyNowPrice;
    private String auctionSelected;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime auctionStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime auctionEndTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Long memberNo;

    public AdminProductResDto(Product product) {
        this.productNo = product.getProductNo();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.salesStatus = product.getSalesStatus();
        this.imdPurchase = product.getImdPurchase() == true ? "YES" : "NO";
        this.buyNowPrice = product.getBuyNowPrice();
        this.auctionSelected = product.getAuctionSelected() == true ? "YES" : "NO";
        this.auctionStartTime = product.getAuctionStartTime();
        this.auctionEndTime = product.getAuctionStartTime();
        this.createdAt = product.getCreatedAt();
        this.memberNo = product.getMember().getMemberNo();
    }
}
