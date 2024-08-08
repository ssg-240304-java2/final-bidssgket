package com.ssg.bidssgket.user.domain.member.domain;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class HtmlTestController {
    @GetMapping("/")
    public String indexController() {
        return "index";
    }
//
//    @GetMapping("/login")
//    public String home() {
//        return "/user/member/login";
//    }
//
//    @GetMapping("/private")
//    public String privatePage(HttpSession session) {
//        String title = (String)session.getAttribute("title");
//        System.out.println("Session 확인!!!!!!!!!!!!!!!!!!!!!!!!!"+ title);
//        return "user/main/mainpage";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        return "user/member/review_seller.admin";
//    }
      @GetMapping("/login")
      public String loginPage() {
          System.out.println(" login = ");
          return "user/member/login";
      }
      @GetMapping("/main")
      public String showReviewPage(Principal principal, HttpSession httpSession) {
          System.out.println("principal = " + principal);

          SessionMember member = (SessionMember) httpSession.getAttribute("member");
          System.out.println("---->"+member);
          return "user/main/mainpage";
      }

}
