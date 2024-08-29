package com.ssg.bidssgket.user.domain.auction.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public ProductResDto getProductById(Long productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productNo));
        return new ProductResDto(product);
    }

    public MemberDTO getMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.map(this::convertToMemberDTO)
                .orElseThrow(() -> new IllegalArgumentException("No member found with the given email"));
    }

    private MemberDTO convertToMemberDTO(Member member) {
        return MemberDTO.builder()
                .memberNo(member.getMemberNo())
                .memberName(member.getMemberName())
                .pwd(member.getPwd())
//                .memberId(member.getMemberId())
                .phone(member.getPhone())
                .email(member.getEmail())
                .memberNickname(member.getMemberNickname())
                .biscuit(member.getBiscuit())
                .isDeleted(member.isDeleted())
                .isPenalty(member.isPenalty())
                .address(member.getAddress())
                .build();
    }

    public int getMinBid(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo);
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productNo));
        if (auctions.isEmpty()) {
            return (int) (product.getAuctionStartPrice() * 0.98);
        } else {
            return (int) (auctions.get(0).getMinTenderPrice() * 1.01);
        }
    }

    public AuctionResponseDto getAuctionByMemberAndProduct(Long memberNo, Long productNo) {
        Auction auction = auctionRepository.findFirstByMemberAndProductNoOrderByBidNoDesc(memberNo, productNo);
        if (auction == null) {
            return null;
        }
        return new AuctionResponseDto(
                auction.getBidNo(),
                auction.getMinTenderPrice(),
                auction.getMaxTenderPrice(),
                auction.getTenderDate(),
                auction.getBidSuccess(),
                auction.getTenderDeleted(),
                auction.getMember(),
                auction.getProduct()
        );
    }

    @Transactional
    public void registerAuction(AuctionReqDto auctionReqDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Product product = productRepository.findById(auctionReqDto.getProductNo())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + auctionReqDto.getProductNo()));
        Auction auction = Auction.createAuction(auctionReqDto, member, product);
        auctionRepository.save(auction);
    }

    @Transactional
    public void modifyAuction(String email, Long productNo, int maxTenderPrice) {
        MemberDTO member = getMemberByEmail(email);
        Auction auction = auctionRepository.findFirstByMemberAndProductNoOrderByBidNoDesc(member.getMemberNo(),productNo);
        auction.updateMaxTenderPrice(maxTenderPrice);
    }

    public int countAuctionsByMemberAndProduct(String email, Long productNo) {
        MemberDTO member = getMemberByEmail(email);
        return auctionRepository.countByMemberNoAndProductNo(member.getMemberNo(), productNo);
    }

    @Transactional
    public void deleteAuction(String email, Long productNo) {
        MemberDTO member = getMemberByEmail(email);
        Auction auction = auctionRepository.findFirstByMemberAndProductNoOrderByBidNoDesc(member.getMemberNo(),productNo);
        auction.updateTenderDeleted(true);
        auctionRepository.save(auction);
    }

    @Transactional
    public void endAuction(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.setSalesStatus(SalesStatus.trading);
        List<Auction> auction = auctionRepository.findAuctionByProductNoOrderByMaxTenderPriceDesc(productNo);
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMinTenderPriceDesc(productNo);

        if (!auction.isEmpty()) {
            Auction minBid = auctions.get(0);
            Auction maxBid = auction.get(0);
            if (minBid.getMinTenderPrice() < product.getAuctionStartPrice()) {
                product.setSalesStatus(SalesStatus.sale_pause);
//                auctionRepository.deleteAll(auction);
            } else {
                Auction successBid = auction.size() > 1 ? auction.get(0) : maxBid;
                maxBid.updateBidSuccess(true);
                /*일반 경매 -> 최소 입찰가가 낙찰가*/
                product.setBidSuccessPrice(successBid.getMinTenderPrice());
            }
        } else {
            product.setSalesStatus(SalesStatus.sale_pause);
            auctionRepository.deleteAll(auction);
        }

        productRepository.save(product);
        auctionRepository.saveAll(auction);
    }

    public boolean isAuctionParticipant(Long memberNo, Long productNo) {
        return auctionRepository.countByMemberNoAndProductNo(memberNo, productNo) > 0;
    }

    public boolean isEventAuctionParticipant(Long memberNo, Long productNo) {
        return auctionRepository.eventCountByMemberNoAndProductNo(memberNo, productNo) > 0;
    }

    public boolean isSeller(Long memberNo, Long productNo) {
        return productRepository.existsByMemberAndProductNo(memberNo, productNo) > 0? true: false;
    }

    @Transactional
    public void  abandonBid(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.setSalesStatus(SalesStatus.sale_pause);

        List<Auction> auctionsToDelete = auctionRepository.findAuctionByProductNo(productNo);
        for (Auction auction : auctionsToDelete) {
            auctionRepository.delete(auction);
        }

        productRepository.save(product);
    }

    public Boolean isWinningBidder(Long memberNo, Long productNo) {
        List<Auction> auction = auctionRepository.findAuctionByProductNoOrderByMaxTenderPriceDesc(productNo);
        if (!auction.isEmpty()) {
            Auction topAuction = auction.get(0);
            Product product = topAuction.getProduct();
//            return topAuction.getMember().getMemberNo().equals(memberNo) && !topAuction.getTenderDeleted();
            if (topAuction.getMinTenderPrice() >= product.getAuctionStartPrice()) {
                return topAuction.getMember().getMemberNo().equals(memberNo) && !topAuction.getTenderDeleted();
            }
        }
        return false;
    }

    public boolean hasBidders(Long productNo) {
        List<Auction> auctions = auctionRepository.findAuctionByProductNo(productNo);
        return !auctions.isEmpty();
    }

    public int getMinPrice(Long productNo) {
        List<Auction> auctions = auctionRepository.findByProductNoOrderByMaxTenderPriceDesc(productNo);
        if (auctions.isEmpty()) {
            Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productNo));
            return (int) (product.getAuctionStartPrice() * 0.95);
        } else {
            return (int) (auctions.get(0).getMaxTenderPrice() * 1.01);
        }
    }

    public List<AuctionResponseDto> findByProductNo(Long productNo) {
        List<Auction> auctions = auctionRepository.findAuctionByProductNo(productNo);
        return auctions.stream().map(auction -> new AuctionResponseDto(
                auction.getBidNo(),
                auction.getMinTenderPrice(),
                auction.getMaxTenderPrice(),
                auction.getTenderDate(),
                auction.getBidSuccess(),
                auction.getTenderDeleted(),
                auction.getMember(),
                auction.getProduct()
        )).collect(Collectors.toList());
    }

    public Boolean isWinningEventBidder(Long memberNo, Long productNo) {
        List<Auction> auction = auctionRepository.findByProductNoOrderByMaxTenderPriceDesc(productNo);
        if (!auction.isEmpty()) {
            Auction topAuction = auction.get(0);
            return topAuction.getMember().getMemberNo().equals(memberNo) && !topAuction.getTenderDeleted();
        }
        return false;
    }

    @Transactional
    public void endEventAuction(Long productNo) {

        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.setSalesStatus(SalesStatus.trading);

        List<Auction> auction = auctionRepository.findByProductNoOrderByMaxTenderPriceDesc(productNo);
        if (auction == null) {
            throw new IllegalStateException("Event auction not found or already deleted");
        }

        if (!auction.isEmpty()) {
            Auction firstBid = auction.get(0);
            Auction successBid = auction.size() > 1 ? auction.get(0) : firstBid;
            firstBid.updateBidSuccess(true);
            product.setBidSuccessPrice(successBid.getMaxTenderPrice());

        } else {
            product.setSalesStatus(SalesStatus.sale_pause);
            auctionRepository.deleteAll(auction);
        }

        productRepository.save(product);
        auctionRepository.saveAll(auction);
    }

    public boolean findAuctionMember(Long productNo, Long memberNo) {
        return auctionRepository.findAuctionMember(productNo, memberNo)> 0? true: false;
    }

    @Transactional
    public void deleteIfAuctionExists(Long productNo) {
        List<Auction> auctions = auctionRepository.findAuctionByProductNo(productNo);
        if(!auctions.isEmpty()){
            auctionRepository.deleteAll(auctions);
        }
    }

    public boolean isEventAuctionExists(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Boolean eventAuction = product.getEventAuction();
        return eventAuction != null && eventAuction;
    }
}
