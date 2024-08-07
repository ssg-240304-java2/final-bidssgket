package com.ssg.bidssgket.user.domain.auction.view;


import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/auction")
public class AuctionViewController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/auctionregist")
    public String showAuctionRegistForm(/*@RequestParam("productNo") Long productNo, */Model model) {
        Long productNo = 1L; // 가정된 productNo 값
        String email = "yujin@gmail.com"; // This should come from the logged-in user context
        List<Member> members = auctionService.getMembersByEmail(email);
        List<Product> products = auctionService.getProductsById(productNo);

        Member member = members.get(0);
        Product product = products.get(0);
        int minBidValue = auctionService.getMinBid(productNo);

        AuctionReqDto auctionReqDto = new AuctionReqDto(minBidValue, 0, productNo);

        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("minBid", minBidValue);
        model.addAttribute("auctionReqDto", auctionReqDto);
        return "user/auction/auctionregist";
    }
}
