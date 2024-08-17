package com.ssg.bidssgket.user.domain.product.view.dto.request;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Acceptance;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductReportReqDto {
    private String complainContent;
    private LocalDateTime complainDate;
    private Acceptance acceptance;
    private Long memberNo;
    private Long productNo;

    @Builder
    public ProductReportReqDto(String acceptance, String complainContent, LocalDateTime complainDate, Long memberNo, Long productNo) {
        this.acceptance = Acceptance.valueOf(acceptance);
        this.complainContent = complainContent;
        this.complainDate = complainDate;
        this.memberNo = memberNo;
        this.productNo = productNo;
    }

    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }

    public void setProductNo(Long productNo) {
        this.productNo = productNo;
    }

    public void setComplainDate(LocalDateTime complainDate) {
        this.complainDate = complainDate;
    }
}
