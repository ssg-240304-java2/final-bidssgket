package com.ssg.bidssgket.user.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer review_no;
    private String comment;
    private Integer biscuit_rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;


    // 리뷰 작성자와 리뷰 대상자의 공통 상품 컬럼?
}
