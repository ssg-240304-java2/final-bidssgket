package com.ssg.bidssgket.admin.view.admin.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserViewController {


    /**
     * 회원 목록 조회 페이지
     */
    @GetMapping("/list")
    public String userListPage() {
        log.info("===== user list page =====");

        return "admin/content/pages/user/user-list";
    }


    /**
     * 회원 상세 조회 페이지
     */
    @GetMapping("/info/{userNo}")
    public String userInfoPage() {
        log.info("===== user info page =====");

        return "admin/content/pages/user/user-list";
    }


    /**
     * 회원 신고 목록 조회 페이지
     */
    @GetMapping("/user/report/list")
    public String userReportListPage() {
        log.info("===== user report list page =====");

        return "admin/content/pages/user/user-report-list";
    }


    /**
     * 회원 신고 상세 조회 페이지
     */
    @GetMapping("/report/info/{complainNo}")
    public String userReportInfoPage() {
        log.info("===== user report info page =====");

        return "admin/content/pages/user/user-report-list";
    }





}
