package com.ssg.bidssgket.user.domain.productwish.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.productwish.application.ProductWishService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishSelectViewController {

    @Autowired
    private ProductWishService productWishService;
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/add")
    public String wishSelect(@RequestParam("productNo") Long productNo, HttpSession httpSession){
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        if (sessionMember != null) {
            String email = sessionMember.getEmail();
            productWishService.addWish(productNo, email);
            return "added";
        } else {
            return "login";
        }
    }

    @PostMapping("/delete")
    public String removeWish(@RequestParam("productNo") Long productNo, HttpSession httpSession) {
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        Member member = auctionService.getMemberByEmail(email);
        productWishService.removeWish(productNo, member.getMemberNo());

        return "redirect:/";  // 필요한 리디렉션 경로로 변경하세요.
    }
}
