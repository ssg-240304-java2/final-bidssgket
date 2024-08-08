package com.ssg.bidssgket.user.domain.product.view.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class productImageDto {
    private Long productImgNo;
    private List<String> productImg;
    private boolean productThumbnail;

    public productImageDto(Long productImgNo, List<String> productImg, boolean productThumbnail) {
        this.productImgNo = productImgNo;
        this.productImg = productImg;
        this.productThumbnail = productThumbnail;
    }
}
