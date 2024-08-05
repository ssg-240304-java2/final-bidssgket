package com.ssg.bidssgket.user.domain.auction.domain.repository;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
