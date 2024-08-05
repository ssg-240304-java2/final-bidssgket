package com.ssg.bidssgket.user.domain.member.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlTestController {
    @GetMapping("")
    public String indexController() {
        return "index";
    }

    @GetMapping("/templates/login")
    public String loginController() {
        return "user/member/login";
    }
}
