package com.ssg.bidssgket.user.domain.auction.domain.repository;



import com.ssg.bidssgket.user.domain.auction.domain.Auction;;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

//    Member findByEmail(String email);

//    Product findByProductNo(Long productNo);

    @Query(value = "SELECT a.* FROM auction a WHERE a.product_no = :productNo ORDER BY a.min_tender_price DESC", nativeQuery = true)
    List<Auction> findByProductNoOrderByMinTenderPriceDesc(@Param("productNo") Long productNo);
}
