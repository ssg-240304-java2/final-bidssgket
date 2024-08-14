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
    private String reviewer;
    private String reviewee;
    private String productNo;

    @Builder
    public ReviewDto(Long reviewNo, String comment, Integer biscuitRating, String reviewer, String reviewee,String productNo) {
        this.reviewNo = reviewNo;
        this.comment = comment;
        this.biscuitRating = biscuitRating;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.productNo = productNo;
    }
}
