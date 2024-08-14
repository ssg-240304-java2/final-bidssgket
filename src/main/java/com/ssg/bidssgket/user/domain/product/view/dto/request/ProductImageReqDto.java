package com.ssg.bidssgket.user.domain.product.view.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImageReqDto {
    private Long productImgNo;
    private String productImg;
    private boolean productThumbnail;
    private Long productNo;
}
