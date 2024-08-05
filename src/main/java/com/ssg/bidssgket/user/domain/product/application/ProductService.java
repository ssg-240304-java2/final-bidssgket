package com.ssg.bidssgket.user.domain.product.application;

import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import com.ssg.bidssgket.user.domain.product.domain.Sales_status;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductImageRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final NcpObjectStorageService ncpObjectStorageService;

    @Transactional
    public Product registProduct(RegistProductReqDto registProductReqDto, List<MultipartFile> productImages) {
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
                .build();

        productRepository.save(product);

        List<ProductImage> images = productImages.stream()
                .map(file -> {
                    String imageUrl = ncpObjectStorageService.uploadFile(file);  // implement this method to upload the image and return its URL
                    return ProductImage.builder()
                            .productImg(imageUrl)
                            .productThumbnail(false)  // Set this based on your logic
                            .product(product)
                            .build();
                }).collect(Collectors.toList());
        productImageRepository.saveAll(images);
        return product;
    }
}
