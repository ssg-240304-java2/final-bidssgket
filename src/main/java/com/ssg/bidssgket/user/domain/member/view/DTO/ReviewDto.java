package com.ssg.bidssgket.user.domain.member.view.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {

    private Long reviewNo;
    private String comment;
    private Integer biscuitRating;
    private String reviewee;
    private String productNo;
    private int productRating;
    private int sellerRating;
    private int buyerRating;


    @Builder
    public ReviewDto(Long reviewNo, String comment, Integer biscuitRating,String reviewee, String productNo, int productRating, int sellerRating,int buyerRating) {
        this.reviewNo = reviewNo;
        this.comment = comment;
        this.biscuitRating = biscuitRating;
        this.reviewee = reviewee;
        this.productNo = productNo;
        this.productRating = productRating;
        this.sellerRating = sellerRating;
        this.buyerRating = buyerRating;
    }
}
