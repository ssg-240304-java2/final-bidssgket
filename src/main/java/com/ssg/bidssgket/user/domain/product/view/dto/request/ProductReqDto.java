package com.ssg.bidssgket.user.domain.product.view.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReqDto {
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
    private List<String> productImg;

}
