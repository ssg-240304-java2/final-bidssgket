package com.ssg.bidssgket.user.domain.eventAuction.application;

import com.ssg.bidssgket.global.util.ncps3.FileDto;
import com.ssg.bidssgket.global.util.ncps3.FileService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductImageRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventAuctionService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileService fileService;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;

    public Product registEventProduct(RegistProductReqDto registProductReqDto, List<MultipartFile> productImages,int auctionDuration) {
        Long memberNo = registProductReqDto.getMemberNo();
        Member member = memberRepository.findById(memberNo).orElseThrow(EntityNotFoundException::new);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(auctionDuration);

        Product product = Product.builder()
                .productName(registProductReqDto.getProductName())
                .category(Category.valueOf(registProductReqDto.getCategory()))
                .salesStatus(SalesStatus.selling)
                .productDesc(registProductReqDto.getProductDesc())
                .eventAuction(true)
                .auctionStartPrice(registProductReqDto.getAuctionStartPrice())
                .auctionStartTime(startTime)
                .auctionEndTime(endTime)
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

    public List<Product> getEventProducts() {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findByEventAuction(now);
    }

    public List<Product> getEventProductsByMember(Long memberNo) {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findEventProductsByMemberNo(memberNo, now);
    }

    public List<Auction> findAllByEventProductNo(Long productNo) {
        Product product =  productRepository.findEventById(productNo);
        return auctionRepository.findAllByProduct(product);
    }

    public List<Product> getEventProductsMain() {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0,10);
        return productRepository.findTop10ByEventAuctionEndDateClosest(now,pageable);
    }

    public List<Product> getEventAuctionProducts(Long productNo) {
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findByEventAuctionProducts(productNo,now);
    }
}
