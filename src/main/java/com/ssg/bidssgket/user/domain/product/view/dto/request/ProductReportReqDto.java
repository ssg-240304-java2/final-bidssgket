package com.ssg.bidssgket.user.domain.product.view.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReportReqDto {
    private Long complainNo;
    private String complainContent;
    private LocalDateTime complainDate;
    private Long memberNo;
    private Long productNo;
}
