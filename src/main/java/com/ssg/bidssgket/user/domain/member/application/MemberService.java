package com.ssg.bidssgket.user.domain.member.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Review;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.member.domain.repository.ReviewRepository;
import com.ssg.bidssgket.user.domain.member.view.DTO.ReviewDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public void submitBuyerReview(ReviewDto reviewDto, Member reviewee, Product product) {
        // 중복 리뷰 체크
        boolean exists = reviewRepository.existsByProduct_ProductNoAndReviewee_MemberNo(product.getProductNo(), reviewee.getMemberNo());
        if (exists) {
            throw new IllegalStateException("Review already exists for this product and member combination.");
        }

        int finalRating = calculateFinalRating(reviewDto.getProductRating(), reviewDto.getSellerRating());
        Review review = Review.createReview(reviewDto.getComment(), finalRating, reviewee, product);
        reviewRepository.save(review);
    }

    private int calculateFinalRating(int productRating, int sellerRating) {
        return (productRating >= 4) ? sellerRating + 5 : sellerRating;
    }

    public void submitSellerReview(ReviewDto reviewDto, Member reviewee, Product product) {
        // 중복 리뷰 체크
        boolean exists = reviewRepository.existsByProduct_ProductNoAndReviewee_MemberNo(product.getProductNo(), reviewee.getMemberNo());
        if (exists) {
            throw new IllegalStateException("Review already exists for this product and member combination.");
        }

        Review review = Review.createReview(reviewDto.getComment(), reviewDto.getSellerRating(), reviewee, product);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateBiscuitForMember(Long memberNo) {
        long count = reviewRepository.countByReviewee_MemberNoAndBiscuitRatingGreaterThanEqual(memberNo, 90);

        if (count >= 3) {
            Member member = memberRepository.findById(memberNo)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            member.incrementBiscuit();
            memberRepository.save(member);
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }
}
