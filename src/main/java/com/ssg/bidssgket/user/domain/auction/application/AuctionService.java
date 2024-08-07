package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public List<Member> getMembersByEmail(String email) {
        return auctionRepository.findMemberByEmail(email);
    }

    public List<Product> getProductsById(Long productNo) {
        return auctionRepository.findProductById(productNo);
    }

    public int getMinBid(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductProductNoOrderByMinTenderPriceDesc(productNo);
        if (auctions.isEmpty()) {
            Product product = auctionRepository.findProductById(productNo).get(0);
            return (int) (product.getAuctionStartPrice() * 1.2);
        } else {
            return (int) (auctions.get(0).getMinTenderPrice() * 1.2);
        }
    }

    public void registerAuction(AuctionReqDto auctionReqDto, String email) {
        List<Member> members = getMembersByEmail(email);
        System.out.println("members = " + members);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("No member found with the given email");
        }
        Member member = members.get(0);

        List<Product> products = getProductsById(auctionReqDto.getProductNo());
        if (products.isEmpty()) {
            throw new IllegalArgumentException("No product found with the given product number");
        }
        Product product = products.get(0);

        Auction auction = Auction.createAuction(auctionReqDto, member, product);
        auctionRepository.save(auction);
    }

    public List<AuctionResponseDto> getAuctionsByProductNo(Long productNo) {
        return auctionRepository.findByProductProductNoOrderByMinTenderPriceDesc(productNo)
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
