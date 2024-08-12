package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ProductService productService;

    /***
     * 경매 등록 폼 불러오기
     * @param model
     * @return
     */
    @GetMapping("/auctionregistform/{productNo}")
    public String showAuctionRegistForm(Model model,  @PathVariable Long productNo, HttpSession httpSession) {
//        log.info("productNo >>>>> {}", productNo);
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
//        System.out.println(email);

        Member member = auctionService.getMemberByEmail(email);
//        log.info("member >>>>>>>>>>. {}", member);
        Product product = auctionService.getProductById(productNo);
//        log.info("product >>>>>>>>>>. {}", product);

        int minBidValue = auctionService.getMinBid(productNo);
//        log.info("minBidValue >>>>>> {}", minBidValue);

        Auction auction = auctionService.getAuctionByMemberAndProduct(member.getMemberNo(), productNo);

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
    @PostMapping("/auctionregist/{productNo}")
    public String registerAuction(@PathVariable("productNo") Long productNo, @RequestParam(required = false) int minTenderPrice, @RequestParam(required = false) int maxTenderPrice, RedirectAttributes redirectAttributes, HttpSession httpSession) {
//        System.out.println("productNo = " + productNo);
//        System.out.println("minTenderPrice = " + minTenderPrice);
//        System.out.println("maxTenderPrice = " + maxTenderPrice);
//        System.out.println(((SessionMember) httpSession.getAttribute("member")).getEmail());
        try {
            String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
//            System.out.println(email);
            int auctionCount = auctionService.countAuctionsByMemberAndProduct(email, productNo);
            if (auctionCount >= 2) {
                redirectAttributes.addFlashAttribute("message", "입찰은 최대 2번까지 가능합니다.");
                return "redirect:/detailBuyer/" + productNo;
            }
            AuctionReqDto auctionReqDto = new AuctionReqDto(minTenderPrice, maxTenderPrice, productNo);
            auctionService.registerAuction(auctionReqDto, email);
            redirectAttributes.addFlashAttribute("message", "경매가 성공적으로 등록되었습니다.");
            return "redirect:/detailAuction/" + productNo;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "경매 등록에 실패했습니다.");
            return "redirect:/detailBuyer/" + productNo;
        }
    }

    /***
     * 경매 수정 폼 불러오기
     * @param model
     * @return
     */
    @GetMapping("/auctionregistmodify/{productNo}")
    public String showAuctionRegistModifyForm(Model model, @PathVariable("productNo") Long productNo, HttpSession httpSession) {
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();

        Member member = auctionService.getMemberByEmail(email);
//        log.info("member >>>>>>>>>>. {}", member);
        Product product = auctionService.getProductById(productNo);
//        log.info("product >>>>>>>>>>. {}", product);

        Auction auction = auctionService.getAuctionByMemberAndProduct(member.getMemberNo(), productNo);

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
    @PostMapping("/auctionregistmodify/{productNo}")
    public String modifyAuction(@PathVariable("productNo") Long productNo, @RequestParam int maxTenderPrice, RedirectAttributes redirectAttributes, HttpSession httpSession) {
//        log.info("productNo >>>>> {}", productNo);
        try {
            String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            auctionService.modifyAuction(email, productNo, maxTenderPrice);
            redirectAttributes.addFlashAttribute("message", "경매가 성공적으로 수정되었습니다.");
            return "redirect:/detailAuction/" + productNo;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "경매 수정에 실패했습니다.");
            return "redirect:/detailAuction/" + productNo;
        }
    }

    @PostMapping("/delete/{productNo}")
    public String deleteAuction(@PathVariable("productNo") Long productNo, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        try {
            String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            auctionService.deleteAuction(email, productNo);
            redirectAttributes.addFlashAttribute("message", "경매가 성공적으로 삭제되었습니다.");
            return "redirect:/detailBuyer/" + productNo;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "경매 삭제에 실패했습니다.");
            return "redirect:/detailAuction/" + productNo;
        }
    }

    @PostMapping("/endAuction")
    public String endAuction(@RequestParam Long productNo,  RedirectAttributes redirectAttributes) {
        try {
            // 경매 종료 로직 처리
            auctionService.endAuction(productNo);
            redirectAttributes.addFlashAttribute("message", "경매가 성공적으로 종료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "경매 종료 처리에 실패했습니다.");
        }
        return "redirect:/detailAuction/" + productNo;
    }

}
