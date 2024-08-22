package com.ssg.bidssgket.user.domain.member.domain.repository;

import com.ssg.bidssgket.user.domain.member.domain.Wish;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMemberEmail(String email);

    @Modifying
    @Query("DELETE FROM Wish w WHERE w.member.email = :email AND w.product.productNo = :productNo")
    void deleteByEmailAndProductNo(@Param("email") String email, @Param("productNo") Long productNo);

}
