package com.ssg.bidssgket.user.domain.member.domain.repository;

import com.ssg.bidssgket.user.domain.member.domain.Wish;
import com.ssg.bidssgket.user.domain.member.view.DTO.WishDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Modifying
    @Query("DELETE FROM Wish w WHERE w.member.email = :email AND w.product.productNo = :productNo")
    void deleteByEmailAndProductNo(@Param("email") String email, @Param("productNo") Long productNo);

    @Query("SELECT w FROM Wish w WHERE w.member.email = :email")
    Page<Wish> findWishesByEmail(@Param("email") String email, Pageable pageable);

}
