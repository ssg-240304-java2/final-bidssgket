package com.ssg.bidssgket.admin.api.product.auction.repository;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminAuctionRepository extends JpaRepository<Auction, Long> {

    Optional<Auction> findByBidSuccessIsTrueAndProduct(@Param("product") Product product);

    List<Auction> findAllByProduct_ProductNo(@Param("productNo") Long productNo);
    List<Auction> findAllByProduct(@Param("product") Product product);
}
