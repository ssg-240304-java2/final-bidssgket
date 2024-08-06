package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.common.AuctionReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/auctionregist")
    public String showAuctionRegistForm(Model model) {
       model.addAttribute("auctionReqDto", new AuctionReqDto());
        return "user/auction/auctionregist";
    }

    @PostMapping("/auctionregist")
    public String registerAuction(AuctionReqDto auctionReqDto, Model model) {
        try {
            auctionService.registerAuction(auctionReqDto);
            model.addAttribute("successMessage", "경매가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "경매 등록에 실패했습니다.");
        }
        return "auctionregist";
    }
}
