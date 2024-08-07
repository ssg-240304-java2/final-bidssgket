package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/auctionregist")
    public String registerAuction(/*@RequestParam("productNo") Long productNo,*/ AuctionReqDto auctionReqDto, Model model) {
        try {
            String email = "yujin@gmail.com"; // This should come from the logged-in user context
            Long productNo = 1L;
            auctionReqDto = new AuctionReqDto(auctionReqDto.getMinTenderPrice(), auctionReqDto.getMaxTenderPrice(), productNo);
            auctionService.registerAuction(auctionReqDto, email);
            model.addAttribute("successMessage", "경매가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "경매 등록에 실패했습니다.");
        }
        return "user/auction/auctionregist";
    }
}
