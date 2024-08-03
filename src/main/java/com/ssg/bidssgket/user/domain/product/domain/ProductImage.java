package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductImageReqDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImgNo;
    private String productImg;
    private boolean productThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    private ProductImage(String productImg, boolean productThumbnail, Product product)  {
        this.productImg = productImg;
        this.productThumbnail = productThumbnail;
        this.product = product;

    }

    public static ProductImage addProductImage(ProductImageReqDto productImageReqDto, Product product) {
        return ProductImage.builder()
                .productImg(productImageReqDto.getProductImg())
                .productThumbnail(productImageReqDto.isProductThumbnail())
                .product(product)
                .build();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
