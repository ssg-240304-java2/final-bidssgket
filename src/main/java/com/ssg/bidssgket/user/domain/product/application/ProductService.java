package com.ssg.bidssgket.user.domain.product.application;

import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.Sales_status;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductImageRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
//    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
//        this.productImageRepository = productImageRepository;
    }

    @Transactional
    public Product registProduct(RegistProductReqDto registProductReqDto) {
        Product product = Product.builder()
                .productName(registProductReqDto.getProductName())
                .category(Category.valueOf(registProductReqDto.getCategory()))
                .salesStatus(Sales_status.valueOf(registProductReqDto.getSalesStatus()))
                .productDesc(registProductReqDto.getProductDesc())
                .imdPurchase(registProductReqDto.getImdPurchase())
                .auctionSelected(registProductReqDto.getAuctionSelected())
                .eventAuction(registProductReqDto.getEventAuction())
                .buyNowPrice(registProductReqDto.getBuyNowPrice())
                .auctionStartPrice(registProductReqDto.getAuctionStartPrice())
                .auctionStartTime(registProductReqDto.getAuctionStartTime())
                .auctionEndTime(registProductReqDto.getAuctionEndTime())
                .bidSuccessPrice(registProductReqDto.getBidSuccessPrice())
                .build();

        return productRepository.save(product);
    }

}
