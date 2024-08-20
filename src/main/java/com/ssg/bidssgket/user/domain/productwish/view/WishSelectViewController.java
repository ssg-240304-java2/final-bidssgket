package com.ssg.bidssgket.user.domain.productwish.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.ssg.bidssgket.user.domain.productwish.application.ProductWishService;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishSelectViewController {

    @Autowired
    private ProductWishService productWishService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @ResponseBody
    @PostMapping("/toggle")
    public String toggleWish(@RequestBody Map<String, Long> data, HttpSession httpSession) {
        /*값을 여러 개 받을 때 dto 사용할수도 있음 -> 지금은 하나라서 Map함수 => json이 key value*/
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        System.out.println("sessionMember = " + sessionMember);
        log.info("data >>>>. {}", data);
        Long productNo = data.get("productNo");
        log.info("productNo>>> {}", productNo);
        if (sessionMember == null) {
            return "fail";
        }
        String email = sessionMember.getEmail();
        productWishService.toggleWish(productNo, email);
        return "success";
    }

    @ResponseBody
    @GetMapping("/status")
    public boolean isProductWished(@RequestParam("productNo") Long productNo, HttpSession httpSession) {
        SessionMember sessionMember = (SessionMember) httpSession.getAttribute("member");
        if (productNo == null) {
            return false;
        }
        if (sessionMember == null) {
            return false;
        }

        String email = sessionMember.getEmail();
        return productWishService.isProductWished(productNo, email);
    }
}
