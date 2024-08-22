package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImageDto {

    private Long productImgNo;
    private String productImg;

    @Builder
    public ProductImageDto(Long productImgNo, String productImg) {
        this.productImgNo = productImgNo;
        this.productImg = productImg;
    }

    public static ProductImageDto setProductImageDto(ProductImage productImage) {
        return ProductImageDto.builder()
                .productImgNo(productImage.getProductImgNo())
                .productImg(productImage.getProductImg())
                .build();
    }
}
