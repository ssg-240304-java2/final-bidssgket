package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/auction")
public class AuctionViewController {

    @Autowired
    private AuctionService auctionService;

    /***
     * 경매 등록 폼 불러오기
     * @param model
     * @return
     */
    @GetMapping("/auctionregist")
    public String showAuctionRegistForm(Model model) {
        Long productNo = 17L;
        String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context

        Member member = auctionService.getMemberByEmail(email);
        log.info("member >>>>>>>>>>. {}", member);
        Product product = auctionService.getProductById(productNo);
        log.info("product >>>>>>>>>>. {}", product);

        int minBidValue = auctionService.getMinBid(productNo);
        log.info("minBidValue >>>>>> {}", minBidValue);

        Auction auction = auctionService.getAuctionByMemberAndProduct(email, productNo);

        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("minBid", minBidValue);
        model.addAttribute("auction", auction);
        return "user/auction/auctionregist";
    }

    /***
     * 폼에서 입력받은 값 auction 테이블에 등록
     * @param minTenderPrice
     * @param maxTenderPrice
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/auctionregist")
    public String registerAuction(@RequestParam(required = false) int minTenderPrice, @RequestParam(required = false) int maxTenderPrice, RedirectAttributes redirectAttributes) {
        try {
            String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context
            Long productNo = 17L;
            auctionService.registerAuction(minTenderPrice, maxTenderPrice, productNo, email);
            redirectAttributes.addFlashAttribute("successMessage", "경매가 성공적으로 등록되었습니다.");
            return "redirect:/user/product/detailAuction";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "경매 등록에 실패했습니다.");
            return "redirect:/auction/auctionregist";
        }
    }

    /***
     * 경매 수정 폼 불러오기
     * @param model
     * @return
     */
    @GetMapping("/auctionregistmodify")
    public String showAuctionRegistModifyForm(Model model) {
        Long productNo = 17L;
        String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context

        Member member = auctionService.getMemberByEmail(email);
        log.info("member >>>>>>>>>>. {}", member);
        Product product = auctionService.getProductById(productNo);
        log.info("product >>>>>>>>>>. {}", product);

        Auction auction = auctionService.getAuctionByMemberAndProduct(email, productNo);

        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("minBid", auction.getMinTenderPrice());
        model.addAttribute("maxBid", auction.getMaxTenderPrice());
        model.addAttribute("auction", auction);
        return "user/auction/auctionregistmodify";
    }

    /***
     * 폼에서 입력받은 값 auction 테이블에 수정
     * @param maxTenderPrice
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/auctionregistmodify")
    public String modifyAuction(@RequestParam(required = false) int maxTenderPrice, RedirectAttributes redirectAttributes) {
        System.out.println("maxTenderPrice = " + maxTenderPrice);
        try {
            String email = "yunjc536147@gmail.com"; // This should come from the logged-in user context
            Long productNo = 17L;
            auctionService.modifyAuction(email, productNo, maxTenderPrice);
            redirectAttributes.addFlashAttribute("successMessage", "경매가 성공적으로 수정되었습니다.");
            return "redirect:/user/main/mainpage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "경매 수정에 실패했습니다.");
            return "redirect:/auction/auctionregistmodify";
        }
    }

    @GetMapping("/count")
    @ResponseBody
    public int countAuctions(@RequestParam String email, @RequestParam Long productNo) {
        return auctionService.countAuctionsByMemberAndProduct(email, productNo);
    }

    @PostMapping("/delete")
    public String deleteAuction(@RequestParam(required = false) Long productNo, RedirectAttributes redirectAttributes) {
        System.out.println("productNo = " + productNo);
        try {
            String email = "yunjc536147@gmail.com";
            auctionService.deleteAuction(email, productNo);
            redirectAttributes.addFlashAttribute("successMessage", "경매가 성공적으로 삭제되었습니다.");
            return "redirect:/user/product/detailAuction";
//            return "redirect:/user/product/detailAuction?productNo=" + productNo;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "경매 삭제에 실패했습니다.");
            return "redirect:/user/product/detailAuction";
        }
    }
}
