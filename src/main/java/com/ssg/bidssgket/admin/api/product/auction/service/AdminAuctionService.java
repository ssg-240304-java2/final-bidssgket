package com.ssg.bidssgket.admin.api.product.auction.service;

import com.ssg.bidssgket.admin.api.product.auction.repository.AdminAuctionRepository;
import com.ssg.bidssgket.admin.api.product.product.repository.AdminProductRepository;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

}
