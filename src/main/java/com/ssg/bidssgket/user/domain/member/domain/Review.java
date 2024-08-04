package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.view.DTO.ReviewDto;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;
    private String comment;
    private Integer biscuitRating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerNo")
    private Member reviewer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewedNo")
    private Member reviewee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    private Review(String comment, Integer biscuitRating,Member reviewer, Member reviewee, Product product) {
        this.comment = comment;
        this.biscuitRating = biscuitRating;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.product = product;
    }

    public static Review createReview(ReviewDto reviewDto,Member member, Product product) {
        return Review.builder()
                .comment(reviewDto.getComment())
                .biscuitRating(reviewDto.getBiscuitRating())
                .reviewer(member)
                .reviewee(member)
                .product(product)
                .build();
    }

    public void setReviewerNo(Member reviewer) {
        this.reviewer = reviewer;
    }

    public void setRevieweeNo(Member reviewee) {
        this.reviewee = reviewee;
    }

    public void setReviewNo(Product product) {
        this.product = product;
    }
}
