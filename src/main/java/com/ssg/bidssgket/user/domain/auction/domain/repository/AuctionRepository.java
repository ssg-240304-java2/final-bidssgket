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

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo AND a.tender_deleted = 'false' ORDER BY a.min_tender_price DESC", nativeQuery = true)
    List<Auction> findByProductNoOrderByMinTenderPriceDesc(Long productNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.member_no = :memberNo AND a.product_no = :productNo AND tender_deleted = false ORDER BY a.bid_no DESC LIMIT 1", nativeQuery = true)
    Auction findFirstByMemberAndProductNoOrderByBidNoDesc(Long memberNo, Long productNo);

    @Query(value = "SELECT COUNT(*) FROM auction a WHERE a.member_no = :memberNo AND a.product_no = :productNo", nativeQuery = true)
    int countByMemberNoAndProductNo(Long memberNo, Long productNo);

    @Query(value = "SELECT COUNT(*) FROM auction a WHERE a.member_no = :memberNo AND a.tender_deleted = 'false' AND a.product_no = :productNo", nativeQuery = true)
    int eventCountByMemberNoAndProductNo(Long memberNo, Long productNo);


    List<Auction> findAllByProduct(Product product);

    @Query(value = "SELECT a.* FROM auction a WHERE a.member_no = :memberNo ORDER BY a.tender_date ASC LIMIT 1", nativeQuery = true)
    List<Auction> findDeleteAuctionByMember(Long memberNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo AND a.tender_deleted = 'false' ORDER BY a.tender_date DESC", nativeQuery = true)
    List<Auction> findAuctionByProductNo(Long productNo);

    Optional<Auction> findByProductAndBidSuccessTrue(Product product);

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo ORDER BY a.max_tender_price DESC", nativeQuery = true)
    List<Auction> findByProductNoOrderByMaxTenderPriceDesc(Long productNo);

    @Query(value = """
        SELECT a.*
        FROM auction a
        INNER JOIN product p ON a.product_no = p.product_no
        INNER JOIN (
            SELECT product_no, MAX(max_tender_price) AS max_price
            FROM auction
            WHERE member_no = :memberNo AND bid_success = false
            GROUP BY product_no
        ) AS latest
        ON a.product_no = latest.product_no AND a.max_tender_price = latest.max_price
        WHERE a.member_no = :memberNo AND a.bid_success = false
        AND p.sales_status = 'selling'
        ORDER BY a.tender_date DESC
    """, nativeQuery = true)
    List<Auction> findAuctionItemsByMember(Long memberNo);

    @Query(value = "SELECT COUNT(*) FROM auction a WHERE a.product_no = :productNo AND a.member_no = :memberNo AND a.tender_deleted='false'", nativeQuery = true)
    int findAuctionMember(Long productNo, Long memberNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo AND a.tender_deleted = 'false' ORDER BY a.max_tender_price DESC", nativeQuery = true)
    List<Auction> findAuctionByProductNoOrderByMaxTenderPriceDesc(Long productNo);
}
