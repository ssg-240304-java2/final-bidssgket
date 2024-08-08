package com.ssg.bidssgket.admin.view.admin.product;

import com.ssg.bidssgket.admin.api.member.application.AdminMemberApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductViewController {


    /**
     * 상품 목록 조회 페이지
     */
    @GetMapping("/list")
    public String productListPage() {
        log.info("===== product list page =====");

        return "admin/content/pages/product/product-list";
    }

    /**
     * 상품 신고 목록 조회 페이지
     */
    @GetMapping("/report/list")
    public String userReportListPage() {
        log.info("===== product report list page =====");

        return "admin/content/pages/product/product-report-list";
    }


    /**
     * 상품 신고 상세 조회 페이지
     */
    @GetMapping("/report/info/{complainNo}")
    public String userReportInfoPage() {
        log.info("===== product report info page =====");

        return "admin/content/pages/product/product-report-info";
    }




}
