package com.ssg.bidssgket.user.domain.auction.domain.repository;



import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value = "SELECT m.* FROM member m WHERE m.email = :email",nativeQuery = true)
    List<Member> findMemberByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM product WHERE product_no = :productNo", nativeQuery = true)
    List<Product> findProductById(@Param("productNo") Long productNo);

    @Query(value = "SELECT * FROM auction WHERE product_no = :productNo ORDER BY min_tender_price DESC", nativeQuery = true)
    List<Auction> findByProductProductNoOrderByMinTenderPriceDesc(@Param("productNo") Long productNo);
}
