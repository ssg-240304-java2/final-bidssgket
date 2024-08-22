package com.ssg.bidssgket.user.domain.member.domain.repository;

import com.ssg.bidssgket.user.domain.member.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByProduct_ProductNoAndReviewee_MemberNo(Long productNo, Long memberNo);

    long countByReviewee_MemberNoAndBiscuitRatingGreaterThanEqual(Long memberNo, int ratingThreshold);
}
