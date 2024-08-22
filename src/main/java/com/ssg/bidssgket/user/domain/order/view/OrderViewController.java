package com.ssg.bidssgket.user.domain.order.view;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderViewController {

    private final MemberRepository memberRepository;

    @GetMapping("/mypage")
    public String myPage(Model model, HttpServletRequest request) {  // 비스킷 온도 동적으로 변경
        SessionMember sessionMember = (SessionMember) request.getSession().getAttribute("member");

        if (sessionMember == null) {
            return "redirect:/login";
        }

        Member member = memberRepository.findByEmail(sessionMember.getEmail()).orElseThrow();
        model.addAttribute("biscuit", member.getBiscuit());
        model.addAttribute("memberNickname",member.getMemberNickname());
        return "/user/order/mypage"; }

    @GetMapping("/order/checkout")
    public String productCheckout() { return "/user/order/checkout"; }
}