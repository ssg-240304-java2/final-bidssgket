package com.ssg.bidssgket.user.domain.auction.domain.repository;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    List<Member> findMembersByEmail(@Param("email") String email);

    @Query("SELECT p FROM Product p WHERE p.productNo = :productNo")
    Optional<Product> findProductById(@Param("productNo") Long productNo);
}
