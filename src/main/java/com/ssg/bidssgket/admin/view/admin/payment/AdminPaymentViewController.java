package com.ssg.bidssgket.admin.view.admin.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/payment")
public class AdminPaymentViewController {


    /**
     * 결제 목록 조회 페이지
     */
    @GetMapping("/list")
    public String userListPage() {
        log.info("===== payment list page =====");

        return "admin/content/pages/payment/payment-list";
    }


    /**
     * 결제 상세 조회 페이지
     */
    @GetMapping("/info/{}")
    public String userInfoPage() {
        log.info("===== user list page =====");

        return "admin/content/pages/user/user-list";
    }






}
