package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;
    private String comment;
    private Integer biscuitRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    private Review(String comment, Integer biscuitRating,Member reviewee, Product product) {
        this.comment = comment;
        this.biscuitRating = biscuitRating;
        this.reviewee = reviewee;
        this.product = product;
    }

    public static Review createReview(String comment, int finalRating, Member reviewee, Product product) {
        return Review.builder()
                .comment(comment)
                .biscuitRating(finalRating)
                .reviewee(reviewee)
                .product(product)
                .build();
    }

    public void setReviewee(Member reviewee) {
        this.reviewee = reviewee;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
