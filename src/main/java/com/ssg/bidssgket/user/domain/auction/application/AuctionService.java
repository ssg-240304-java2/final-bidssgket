package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("No member found with the given email"));
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

    public Auction getAuctionByMemberAndProduct(Long memberNo, Long productNo) {
        return auctionRepository.findFirstByMemberAndProductNoOrderByBidNoDesc(memberNo, productNo);
    }

    @Transactional
    public void registerAuction(AuctionReqDto auctionReqDto, String email) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(auctionReqDto.getProductNo());
        Auction auction = Auction.createAuction(auctionReqDto, member, product);
        auctionRepository.save(auction);
    }

    @Transactional
    public void modifyAuction(String email, Long productNo, int maxTenderPrice) {
        Member member = getMemberByEmail(email);
        Auction auction = getAuctionByMemberAndProduct(member.getMemberNo(), productNo);
        auction.updateMaxTenderPrice(maxTenderPrice);
    }

    public int countAuctionsByMemberAndProduct(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        return auctionRepository.countByMemberNoAndProductNo(member.getMemberNo(), productNo);
    }

    @Transactional
    public void deleteAuction(String email, Long productNo) {
        Member member = getMemberByEmail(email);
        Auction auction = getAuctionByMemberAndProduct(member.getMemberNo(), productNo);
        auction.updateTenderDeleted(true);
        auctionRepository.save(auction);
    }

}
