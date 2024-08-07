package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return productRepository.findProductByProductNo(productNo);
    }
    public Member getMemberByEmail(String email) {
        System.out.println("email = " + email);
        return memberRepository.findByEmail(email);
    }

    public int getMinBid(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo);
        System.out.println("auctions = " + auctions);
        if (auctions.isEmpty()) {
            Product product = getProductById(productNo);
            return (int) (product.getAuctionStartPrice() * 1.2);
        } else {
            return (int) (auctions.get(0).getMinTenderPrice() * 1.2);
        }
    }

    public void registerAuction(AuctionReqDto auctionReqDto, String email) {
        Member member = getMemberByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("No member found with the given email");
        }

        Product product = getProductById(auctionReqDto.getProductNo());
        if (product == null) {
            throw new IllegalArgumentException("No product found with the given product number");
        }

        Auction auction = Auction.createAuction(auctionReqDto, member, product);
        System.out.println("auction = " + auction);
        auctionRepository.save(auction);
    }

    public List<AuctionResponseDto> getAuctionsByProductNo(Long productNo) {
        return auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo)
                .stream()
                .map(auction -> new AuctionResponseDto(
                        auction.getBidNo(),
                        auction.getMinTenderPrice(),
                        auction.getMaxTenderPrice(),
                        auction.getTenderDate(),
                        auction.getBidSuccess(),
                        auction.getTenderDeleted(),
                        auction.getMember().getMemberNickname(),
                        auction.getProduct().getProductName()
                ))
                .collect(Collectors.toList());
    }


}
