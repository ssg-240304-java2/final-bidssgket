package com.ssg.bidssgket.admin.view.admin.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class AdminMemberViewController {


    /**
     * 회원 목록 조회 페이지
     */
    @GetMapping("/list")
    public String userListPage() {
        log.info("===== member list page =====");

        return "admin/content/pages/member/member-list";
    }


    /**
     * 회원 상세 조회 페이지
     */
    @GetMapping("/info/{userNo}")
    public String userInfoPage() {
        log.info("===== member info page =====");

        return "admin/content/pages/member/member-info";
    }







}
