/*
package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productNo) {
        return productRepository.findById(productNo).orElseThrow(IllegalAccessError::new);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public int getMinBid(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo);
        if (auctions.isEmpty()) {
            Product product = getProductById(productNo);
            return (int) (product.getAuctionStartPrice() * 1.2);
        } else {
            return (int) (auctions.get(0).getMinTenderPrice() * 1.2);
        }
    }

    @Transactional
    public void registerAuction(int minTenderPrice, int maxTenderPrice, Long productNo, String email) {
        Member member = getMemberByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("No member found with the given email");
        }

        Product product = getProductById(productNo);
        if (product == null) {
            throw new IllegalArgumentException("No product found with the given product number");
        }

        Auction auction = Auction.builder()
                .minTenderPrice(minTenderPrice)
                .maxTenderPrice(maxTenderPrice)
                .tenderDate(LocalDateTime.now())
                .bidSuccess(false)
                .tenderDeleted(false)
                .member(member)
                .product(product)
                .build();

        auctionRepository.save(auction);
    }

    public Auction getAuctionByMemberAndProduct(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("No member found with the given email");
        }

        return auctionRepository.findByMemberAndProduct(member.getMemberNo(), productNo)
                .orElseThrow(() -> new IllegalArgumentException("No auction found for the given member and product"));
    }

    @Transactional
    public void modifyAuction(String email, Long productNo, int maxTenderPrice) {
        Auction auction = getAuctionByMemberAndProduct(email, productNo);
        auction.updateMaxTenderPrice(maxTenderPrice);
    }


    @Transactional
    public void deleteAuction(String email, Long productNo) {
        Auction auction = getAuctionByMemberAndProduct(email, productNo);
        auction.updateTenderDeleted(true);
        auctionRepository.save(auction);
    }

    public int getAuctionCountByMemberAndProduct(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productNo);
        return auctionRepository.countByMemberAndProduct(member, product);}
}
*/
package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productNo) {
        return productRepository.findById(productNo).orElseThrow(IllegalAccessError::new);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public int getMinBid(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo);
        if (auctions.isEmpty()) {
            Product product = getProductById(productNo);
            return (int) (product.getAuctionStartPrice() * 1.01);
        } else {
            return (int) (auctions.get(0).getMinTenderPrice() * 1.01);
        }
    }

    public Auction getAuctionByMemberAndProduct(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        return auctionRepository.findFirstByMemberAndProductNoOrderByBidNoDesc(member.getMemberNo(), productNo);
    }

    @Transactional
    public void registerAuction(int minTenderPrice, int maxTenderPrice, Long productNo, String email) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productNo);

        Auction auction = Auction.createAuction(new AuctionReqDto(minTenderPrice, maxTenderPrice, productNo), member, product);
        auctionRepository.save(auction);
    }

    @Transactional
    public void modifyAuction(String email, Long productNo, int maxTenderPrice) {
        Auction auction = getAuctionByMemberAndProduct(email, productNo);
        auction.updateMaxTenderPrice(maxTenderPrice);
    }

    public int countAuctionsByMemberAndProduct(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        return auctionRepository.countByMemberNoAndProductNo(member.getMemberNo(), productNo);
    }

    public void deleteAuction(String email, Long productNo) {
        Auction auction = getAuctionByMemberAndProduct(email, productNo);
        auction.updateTenderDeleted(true);
        auctionRepository.save(auction);
    }
}

