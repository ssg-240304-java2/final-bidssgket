package com.ssg.bidssgket.user.domain.product.view.dto.request;

import lombok.Data;

@Data
public class ProductImageReqDto {
    private Long productImgNo;
    private String productImg;
    private boolean productThumbnail;
    private int productNo;
}
