package com.ssg.bidssgket.user.domain.product.view.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductReportReqDto {
    private Long complainNo;
    private String complainContent;
    private LocalDateTime complainDate;
    private Long memberNo;
    private Long productNo;
}
