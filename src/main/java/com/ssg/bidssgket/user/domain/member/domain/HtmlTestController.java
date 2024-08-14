package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class HtmlTestController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/member/login";
    }


//    @GetMapping("/private")
//    public String privatePage() {
//        return "user/main/mainpage";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        return "user/member/review_seller.admin";
//    }

//      @GetMapping()
//      public String showReviewPage(Principal principal, HttpSession httpSession) {
//          System.out.println("principal = " + principal);
//          SessionMember member = (SessionMember) httpSession.getAttribute("member");
//          System.out.println("---->"+member); //--> 세션확인용
//          return "";
//      }
}