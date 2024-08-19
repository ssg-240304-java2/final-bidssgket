package com.ssg.bidssgket.user.domain.auction.domain.repository;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo ORDER BY a.min_tender_price DESC", nativeQuery = true)
    List<Auction> findByProductNoOrderByMinTenderPriceDesc(Long productNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.member_no = :memberNo AND a.product_no = :productNo AND tender_deleted = false ORDER BY a.bid_no DESC LIMIT 1", nativeQuery = true)
    Auction findFirstByMemberAndProductNoOrderByBidNoDesc(Long memberNo, Long productNo);

    @Query(value = "SELECT COUNT(*) FROM auction a WHERE a.member_no = :memberNo AND a.product_no = :productNo", nativeQuery = true)
    int countByMemberNoAndProductNo(Long memberNo, Long productNo);

    List<Auction> findAllByProduct(Product product);

    @Query(value = "SELECT a.* FROM auction a WHERE a.member_no = :memberNo ORDER BY a.tender_date ASC LIMIT 1", nativeQuery = true)
    List<Auction> findDeleteAuctionByMember(Long memberNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo AND a.tender_deleted = 'false' ORDER BY a.tender_date ASC", nativeQuery = true)
    List<Auction> findAuctionByProductNo(Long productNo);

    Optional<Auction> findByProductAndBidSuccessTrue(Product product);
}
