package com.ssg.bidssgket.admin.view.admin.product;

import com.ssg.bidssgket.admin.api.member.application.AdminMemberApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * 상품 목록 조회 페이지
     */
    @GetMapping("/info/{productNo}")
    public String productInfoPage() {
        log.info("===== product info page =====");

        return "admin/content/pages/product/product-info";
    }



    /**
     * 상품 신고 대기 목록 조회 페이지
     */
    @GetMapping("/report/waiting/list")
    public String userReportWaitingListPage() {
        log.info("===== product report list page =====");

        return "admin/content/pages/product/product-report-waiting-list";
    }


    /**
     * 상품 신고 거절 목록 조회 페이지
     */
    @GetMapping("/report/rejection/list")
    public String userReportRejectionListPage() {
        log.info("===== product report list page =====");

        return "admin/content/pages/product/product-report-rejection-list";
    }


    /**
     * 상품 신고 승인 목록 조회 페이지
     */
    @GetMapping("/report/approval/list")
    public String userReportApprovalListPage() {
        log.info("===== product report list page =====");

        return "admin/content/pages/product/product-report-approval-list";
    }




    /**
     * 상품 신고 상세 조회 페이지
     */
    @GetMapping("/report/info/{complainNo}")
    public String userReportInfoPage(@PathVariable(name = "complainNo") Long complainNo, Model model) {
        log.info("===== product report info page =====");

        return "admin/content/pages/product/product-report-info";
    }




}
