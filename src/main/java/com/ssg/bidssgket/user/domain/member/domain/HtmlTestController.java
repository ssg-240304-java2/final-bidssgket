package com.ssg.bidssgket.user.domain.member.domain;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String privatePage(@RequestParam("token") String token) {

        System.out.println("access_token = " + token);

        return "user/main/mainpage";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "user/member/review_seller.admin";
    }
}
