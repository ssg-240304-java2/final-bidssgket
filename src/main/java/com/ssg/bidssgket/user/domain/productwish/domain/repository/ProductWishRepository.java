package com.ssg.bidssgket.user.domain.productwish.domain.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.WishDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductWishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    @Query(value="SELECT w.* FROM wish w WHERE w.member_no = :memberNo AND w.product_no = :productNo", nativeQuery = true)
    Wish findByMemberNoAndProductNo(@Param("memberNo") Long memberNo, @Param("productNo") Long productNo);

    @Query(value="SELECT w.product_no FROM wish w WHERE w.member_no = :memberNo", nativeQuery = true)
    List<Long> findProductNoByMemberNo(@Param("memberNo") Long memberNo);
}