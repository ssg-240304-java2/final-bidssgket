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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/auction")
public class AuctionViewController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/auctionregist")
    public String showAuctionRegistForm(Model model) {
        Long productNo = 1L;
        String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context

        Member member = auctionService.getMemberByEmail(email);
        log.info("member >>>>>>>>>>. {}", member);
        Product product = auctionService.getProductById(productNo);
        log.info("product >>>>>>>>>>. {}", product);

        int minBidValue = auctionService.getMinBid(productNo);
        log.info("minBidValue >>>>>> {}", minBidValue);

        AuctionReqDto auctionReqDto = new AuctionReqDto(minBidValue, 0, productNo);

        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("minBid", minBidValue);
        model.addAttribute("auctionReqDto", auctionReqDto);
        return "user/auction/auctionregist";
    }

    @PostMapping("/auctionregist")
    public String registerAuction(@RequestParam int minTenderPrice, @RequestParam int maxTenderPrice, RedirectAttributes redirectAttributes) {
        try {
            String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context
            Long productNo = 1L;
            AuctionReqDto auctionReqDto = new AuctionReqDto(minTenderPrice, maxTenderPrice, productNo);
            auctionService.registerAuction(auctionReqDto, email);
            redirectAttributes.addFlashAttribute("successMessage", "경매가 성공적으로 등록되었습니다.");
            return "redirect:/user/product/detailAuction";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "경매 등록에 실패했습니다.");
            return "redirect:/auction/auctionregist";
        }
    }

}
