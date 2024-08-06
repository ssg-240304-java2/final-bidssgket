package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.common.AuctionRegistDto;


import com.ssg.bidssgket.user.domain.auction.common.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public void registerAuction(AuctionReqDto auctionReqDto) {
        List<Member> members = auctionRepository.findMembersByEmail(auctionReqDto.getEmail());
        if (!members.isEmpty()) {
            Member member = members.get(0); // 이메일이 중복되지 않는다고 가정하고 첫 번째 멤버를 사용
            Product product = auctionRepository.findProductById(auctionReqDto.getProductNo())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

            Auction auction = Auction.createAuction(auctionReqDto, member, product);
            auctionRepository.save(auction);
        } else {
            throw new IllegalArgumentException("Invalid member email");
        }
    }
}
