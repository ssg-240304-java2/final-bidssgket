package com.ssg.bidssgket.admin.api.product.productreport.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.bidssgket.user.domain.product.domain.Category;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductReport;
import com.ssg.bidssgket.user.domain.product.domain.Sales_status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminProductReportResDto {
    private Long complainNo;
    private Long productNo;
    private String complainContent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime complainDate;
    private Long memberNo;


    public AdminProductReportResDto(ProductReport productReport) {
        this.complainNo = productReport.getComplainNo();
        this.productNo = productReport.getProduct().getProductNo();
        this.complainContent = productReport.getComplainContent();
        this.complainDate = productReport.getComplainDate();
        this.memberNo = productReport.getMember().getMemberNo();
    }
}
