package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {

    private Long productNo;
    private ProductImageDto productImageDto;
    private String productName;
    private Integer auctionStartPrice;

    @Builder
    public ProductDto(Long productNo, ProductImageDto productImageDto, String productName,Integer auctionStartPrice) {
        this.productNo = productNo;
        this.productImageDto = productImageDto;
        this.productName = productName;
        this.auctionStartPrice = auctionStartPrice;
    }

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .productNo(product.getProductNo())
                .productName(product.getProductName())
                .auctionStartPrice(product.getAuctionStartPrice())
                .build();
        ProductImageDto productImageDto = ProductImageDto.setProductImageDto(product.getProductImages().get(0));
        productDto.setProductImageDto(productImageDto);
        return productDto;
    }

    private void setProductImageDto(ProductImageDto productImageDto) {
        this.productImageDto = productImageDto;
    }

}
