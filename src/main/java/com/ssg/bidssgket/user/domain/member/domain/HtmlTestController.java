package com.ssg.bidssgket.user.domain.member.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlTestController {
    @GetMapping("/")
    public String indexController() {
        return "index";
    }

    @GetMapping("/login")
    public String home() {
        return "/user/member/login";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "user/member/review_buyer.private";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "user/member/review_seller.admin";
    }
}
