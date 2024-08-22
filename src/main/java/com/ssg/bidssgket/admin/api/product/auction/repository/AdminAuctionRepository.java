package com.ssg.bidssgket.admin.api.product.auction.repository;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminAuctionRepository extends JpaRepository<Auction, Long> {

    Optional<Auction> findByBidSuccessIsTrueAndProduct(@Param("product") Product product);
}
