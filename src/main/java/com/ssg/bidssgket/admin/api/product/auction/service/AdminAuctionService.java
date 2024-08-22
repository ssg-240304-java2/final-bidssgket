package com.ssg.bidssgket.admin.api.product.auction.service;

import com.ssg.bidssgket.admin.api.product.auction.repository.AdminAuctionRepository;
import com.ssg.bidssgket.admin.api.product.product.repository.AdminProductRepository;
import com.ssg.bidssgket.admin.notification.dto.AuctionMemberNoResDto;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminAuctionService {
    private final AdminAuctionRepository adminAuctionRepository;
    private final AdminProductRepository adminProductRepository;

    /**
     * 경매 마감, Notification
     */
    public List<Long> sendAuctionClosed(Long productNo){

        List<Long> memberNoList = new ArrayList<>();
        Product product = adminProductRepository.findById(productNo).orElseThrow(() -> new RuntimeException("Product가 존재하지 않습니다."));
        Long sellerMemberNo = product.getMember().getMemberNo();

        Auction auction = adminAuctionRepository.findByBidSuccessIsTrueAndProduct(product).orElseThrow(() -> new RuntimeException("Auction이 존재하지 않습니다."));
        Long buyerMemberNo = auction.getMember().getMemberNo();

        memberNoList.add(sellerMemberNo);
        memberNoList.add(buyerMemberNo);

        log.info("seller : " + sellerMemberNo);
        log.info("buyer : " + buyerMemberNo);


        return memberNoList;
    }

    /**
     * 상품 상태 변경
     * @param productNo
     */
    public List<AuctionMemberNoResDto> getAllParticipants(Long productNo) {
        Product product = adminProductRepository.findById(productNo).orElseThrow(() -> new RuntimeException("Product가 존재하지 않습니다."));

        return adminProductRepository.findAllByProduct(product).stream()
                .map(AuctionMemberNoResDto::new)
                .collect(Collectors.toList());

    }



}
