package com.ssg.bidssgket.user.domain.product.domain.repository;

import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product p SET p.productName = :productName, p.productDesc = :productDesc, p.imdPurchase = :imdPurchase, " +
            "p.auctionSelected = :auctionSelected, p.eventAuction = :eventAuction, p.buyNowPrice = :buyNowPrice, " +
            "p.auctionStartPrice = :auctionStartPrice, p.auctionStartTime = :auctionStartTime, p.auctionEndTime = :auctionEndTime, " +
            "p.category = :category, p.salesStatus = :salesStatus WHERE p.productNo = :productNo")
    int updateProduct(@Param("productNo") Long productNo, @Param("productName") String productName, @Param("productDesc") String productDesc,
                      @Param("imdPurchase") Boolean imdPurchase, @Param("auctionSelected") Boolean auctionSelected,
                      @Param("eventAuction") Boolean eventAuction, @Param("buyNowPrice") Integer buyNowPrice,
                      @Param("auctionStartPrice") Integer auctionStartPrice, @Param("auctionStartTime") LocalDateTime auctionStartTime,
                      @Param("auctionEndTime") LocalDateTime auctionEndTime, @Param("category") Category category,
                      @Param("salesStatus") SalesStatus salesStatus);

}
