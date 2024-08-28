package com.ssg.bidssgket.user.domain.product.domain.repository;

import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;

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

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.salesStatus = 'selling' ORDER BY p.auctionEndTime ASC")
    List<Product> findTop10ByAuctionEndDateClosest(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.category = :category  AND  p.salesStatus = 'selling' ORDER BY p.auctionEndTime ASC")
    List<Product> findByCategory(@Param("category") Category category, @Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.auctionSelected = true AND p.salesStatus = 'selling' ORDER BY p.auctionEndTime ASC")
    List<Product> findByAuctionSelected(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.salesStatus = 'selling' AND p.member.memberNo = :memberNo")
    List<Product> findByMemberNo(@Param("memberNo") Long memberNo, @Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.productName LIKE %:search% OR p.productDesc LIKE %:search% AND p.salesStatus = 'selling' ORDER BY p.createdAt ASC ")
    List<Product> searchBySearch(@Param("search") String search, @Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.salesStatus = 'selling' ORDER BY p.createdAt DESC ")
    List<Product> findAllProduct( @Param("now") LocalDateTime now);

    @Query(value = "SELECT CASE WHEN count(*)>0 THEN TRUE ELSE FALSE END FROM product p WHERE p.member_no= :memberNo AND p.product_no= :productNo", nativeQuery = true)
    Long existsByMemberAndProductNo(Long memberNo, Long productNo);
    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.eventAuction = true AND p.salesStatus = 'selling' ORDER BY p.auctionEndTime ASC")
    List<Product> findByEventAuction(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.salesStatus = 'selling' AND p.member.memberNo = :memberNo AND p.eventAuction = true")
    List<Product> findEventProductsByMemberNo(@Param("memberNo") Long memberNo, @Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.productNo = :productNo AND p.eventAuction = true")
    Product findEventById(@Param("productNo") Long productNo);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.salesStatus = 'selling' AND p.eventAuction = true ORDER BY p.auctionEndTime ASC")
    List<Product> findTop10ByEventAuctionEndDateClosest(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.auctionEndTime > :now AND p.eventAuction = true AND p.salesStatus = 'selling' AND p.productNo = :productNo ORDER BY p.auctionEndTime ASC")
    List<Product> findByEventAuctionProducts(@Param("productNo") Long productNo, @Param("now") LocalDateTime now);

    @Query("SELECT p FROM Product p WHERE p.member.memberNo = :memberNo AND p.salesStatus = 'selling' ORDER BY p.createdAt DESC ")
    List<Product> findAuctionSalesItemsByMember(@Param("memberNo") Long memberNo);

    @Query("SELECT p FROM Product p WHERE p.member.memberNo = :memberNo AND p.salesStatus = 'sale_completed' ORDER BY p.createdAt DESC ")
    List<Product> getSaleCompletedProducts(Long memberNo);
}
