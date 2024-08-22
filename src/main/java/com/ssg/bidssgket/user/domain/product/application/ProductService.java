package com.ssg.bidssgket.user.domain.product.application;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.auction.view.dto.FailedProductReqDto;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.domain.*;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductImageRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductReportRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReportReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileService fileService;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Product registProduct(RegistProductReqDto registProductReqDto, List<MultipartFile> productImages) {
        Long memberNo = registProductReqDto.getMemberNo();
        Member member = memberRepository.findById(memberNo).orElseThrow(EntityNotFoundException::new);

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
                .member(member)
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
    public void updateProduct(ProductReqDto updateProduct) {
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

        // 업데이트된 제품 저장
        productRepository.save(foundProduct);
    }

    @Transactional
    public void deleteImage(Long deleteImageId) {
        ProductImage productImage = productImageRepository.findById(deleteImageId)
                .orElseThrow(() -> new IllegalArgumentException("ProductImage not found with id: " + deleteImageId));

        System.out.println("deleteImageId = " + deleteImageId);
        productImageRepository.deleteById(deleteImageId);
        productImageRepository.flush();
    }

    @Transactional
    public void addImage(List<MultipartFile> productImages,Long productNo) {
        // productNo를 이용해 Product 엔티티를 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with productNo: " + productNo));

        List<FileDto> fileDtos = fileService.uploadFiles(productImages, "product-images");
        for (FileDto fileDto : fileDtos) {
            ProductImage productImage = ProductImage.builder()
                    .productImg(fileDto.getUploadFileUrl())
                    .productThumbnail(false) // 필요에 따라 썸네일 여부 설정
                    .product(product)
                    .build();
            productImageRepository.save(productImage);
        }
    }

    @Transactional
    public void deleteProductByNo(Long productNo) {
        productRepository.deleteById(productNo);
        productImageRepository.deleteById(productNo);
        productRepository.flush();
    }

    public List<Product> getMainpageProduct() {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0,10);
        return productRepository.findTop10ByAuctionEndDateClosest(now,pageable);
    }

    public List<Product> getProductsByCategory(Category category) {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findByCategory(category,now);
    }

    public List<Product> getAllProducts() {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findAllProduct(now);
    }

    public List<Product> getAuctionProducts() {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findByAuctionSelected(now);
    }

    public List<Product> getProductsByMember(Long memberNo) {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findByMemberNo(memberNo,now);
    }

    public List<Auction> findAllByProductNo(Long productNo) {
        // productNo로 Product를 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with productNo: " + productNo));

        // 조회된 Product를 이용해 Auction 목록 조회
        return auctionRepository.findAllByProduct(product);
    }

    public List<Product> searchProducts(String search) {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.searchBySearch(search,now);
    }

    public List<Auction> findDeleteAuction(Long memberNo) {
        return auctionRepository.findDeleteAuctionByMember(memberNo);
    }

    public List<Auction> findAuctionByProductNo(Long productNo) {
        return auctionRepository.findAuctionByProductNo(productNo);
    }


    /***
     * 유찰된 상품 판매 재개 시 판매 상태 변화
     * @param productNo
     * @param salesStatus
     */
    @Transactional
    public void changeProductSalesStatus(Long productNo, SalesStatus salesStatus) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. productNo: " + productNo));
        product.setSalesStatus(salesStatus);
        productRepository.save(product);
    }

    public void updateFailedBidProduct(FailedProductReqDto updateFailedBidProduct) {
        Product failedproduct = productRepository.findById(updateFailedBidProduct.getProductNo())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with productNo: " + updateFailedBidProduct.getProductNo()));

        // Update product fields
        failedproduct.setProductName(updateFailedBidProduct.getProductName());
        failedproduct.setSalesStatus(updateFailedBidProduct.getSalesStatus());
        failedproduct.setProductDesc(updateFailedBidProduct.getProductDesc());
        failedproduct.setImdPurchase(updateFailedBidProduct.getImdPurchase());
        failedproduct.setAuctionSelected(updateFailedBidProduct.getAuctionSelected());
        failedproduct.setEventAuction(updateFailedBidProduct.getEventAuction());
        failedproduct.setBuyNowPrice(updateFailedBidProduct.getBuyNowPrice());
        failedproduct.setAuctionStartPrice(updateFailedBidProduct.getAuctionStartPrice());
        failedproduct.setAuctionStartTime(updateFailedBidProduct.getAuctionStartTime());
        failedproduct.setAuctionEndTime(updateFailedBidProduct.getAuctionEndTime());

        // 업데이트된 제품 저장
        productRepository.save(failedproduct);
    }

}

