package com.ssg.bidssgket.user.domain.product.application;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductImageRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileService fileService;

    @Transactional
    public Product registProduct(RegistProductReqDto registProductReqDto, List<MultipartFile> productImages) {
        Product product = Product.builder()
                .productName(registProductReqDto.getProductName())
                .category(Category.valueOf(registProductReqDto.getCategory()))
                .salesStatus(SalesStatus.selling)
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

        // 2. 파일 업로드 및 이미지 정보 저장
        List<FileDto> fileDtos = fileService.uploadFiles(productImages, "product-images");
        for (FileDto fileDto : fileDtos) {
            ProductImage productImage = ProductImage.builder()
                    .productImg(fileDto.getUploadFileUrl())
                    .productThumbnail(false) // 필요에 따라 썸네일 여부 설정
                    .product(product)
                    .build();
            productImageRepository.save(productImage);
        }
        return product;
    }

    public ProductResDto findProductByNo(Long productNo) {
       // ProductImage productImage = productImageRepository.findByProductImage_ProductNo(productNo);
        Product product = productRepository.findById(productNo).get();
        log.info("ProductByNo:{}", productNo);
        log.info("product:{}", productNo);
        ProductResDto productResDto = ProductResDto.builder()
                .product(product)
                .build();
        log.info("productResDto:{}", productResDto.getProductName());
        log.info("productResDto:{}", productResDto.getProductImages());
        return productResDto;
    }

    @Transactional
    public void updateProduct(ProductReqDto updateProduct,List<MultipartFile> productImages, List<Long> deletedImageIds) {
        Product foundProduct = productRepository.findById(updateProduct.getProductNo())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with productNo: " + updateProduct.getProductNo()));

        // Update product fields
        foundProduct.setProductName(updateProduct.getProductName());
        foundProduct.setSalesStatus(updateProduct.getSalesStatus());
        foundProduct.setProductDesc(updateProduct.getProductDesc());
        foundProduct.setImdPurchase(updateProduct.getImdPurchase());
        foundProduct.setAuctionSelected(updateProduct.getAuctionSelected());
        foundProduct.setEventAuction(updateProduct.getEventAuction());
        foundProduct.setBuyNowPrice(updateProduct.getBuyNowPrice());
        foundProduct.setAuctionStartPrice(updateProduct.getAuctionStartPrice());
        foundProduct.setAuctionEndTime(updateProduct.getAuctionEndTime());

        List<FileDto> fileDtos = fileService.uploadFiles(productImages, "product-images");
        for (FileDto fileDto : fileDtos) {
            ProductImage productImage = ProductImage.builder()
                    .productImg(fileDto.getUploadFileUrl())
                    .productThumbnail(false) // 필요에 따라 썸네일 여부 설정
                    .product(foundProduct)
                    .build();
            productImageRepository.save(productImage);
        }

        // 업데이트된 제품 저장
        productRepository.save(foundProduct);

    }

    @Transactional
    public void deleteImage(Long deleteImageId) {
        ProductImage productImage = productImageRepository.findById(deleteImageId)
                .orElseThrow(() -> new IllegalArgumentException("ProductImage not found with id: " + deleteImageId));
        productImageRepository.deleteById(deleteImageId);
        productImageRepository.flush();
    }
}

