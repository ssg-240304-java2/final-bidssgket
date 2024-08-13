package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

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

    /***
     * 경매 입찰 등록 삭제
     * @param productNo
     * @param redirectAttributes
     * @param httpSession
     * @return
     */
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

    /*@PostMapping("/endAuction/{productNo}")
    public String endAuction(@PathVariable("productNo") Long productNo, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        ProductResDto product = productService.findProductByNo(productNo);

        boolean isAuctionEnded = product.getAuctionEndTime().isBefore(LocalDateTime.now());

        if (isAuctionEnded) {
            // 경매 종료 처리
            auctionService.endAuction(productNo);

            // 경매 상태가 'sale_pause'인 경우, 판매자가 보는 페이지로 이동
            if (product.getSalesStatus().equals(SalesStatus.sale_pause)) {
                redirectAttributes.addFlashAttribute("message", "경매가 종료되었습니다.");
                return "redirect:/bidFailed";  // 판매자가 보는 페이지로 이동
            } else {
                // 입찰자에게 경매 성공 또는 실패 메시지와 함께 페이지 이동
                redirectAttributes.addFlashAttribute("message", "경매가 정상적으로 종료되었습니다.");
                return "redirect:/bidSuccess";  // 입찰자가 보는 페이지로 이동
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "경매가 아직 종료되지 않았습니다.");
            return "redirect:/detailAuction/" + productNo;
        }
    }*/
    @GetMapping("/endAuction/{productNo}")
    public String endAuction(@PathVariable("productNo") Long productNo, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        log.info("productNo =====> {}", productNo);
        String email = null;
        Long memberNo = null;

        // 세션에서 로그인된 사용자의 이메일을 가져옵니다.
        if (httpSession.getAttribute("member") != null) {
            email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            System.out.println("email = " + email);
            memberNo = auctionService.getMemberByEmail(email).getMemberNo(); // 이메일로 회원 번호를 가져옵니다.
        }

        ProductResDto product = productService.findProductByNo(productNo);

        boolean isAuctionEnded = product.getAuctionEndTime().isBefore(LocalDateTime.now());
        System.out.println("isAuctionEnded = " + isAuctionEnded);

        boolean isSeller = auctionService.isSeller(memberNo, productNo);
        System.out.println("isSeller = " + isSeller);

        boolean isAuctionParticipant = auctionService.isAuctionParticipant(memberNo, productNo);
        System.out.println("isAuctionParticipant = " + isAuctionParticipant);

        if (isSeller && product.getSalesStatus().equals(SalesStatus.sale_pause)) {
            auctionService.endAuction(productNo);
            redirectAttributes.addFlashAttribute("message", "경매가 종료되었습니다.");
            return "redirect:/auction/bidFailed/" + productNo;
        }
        if(isSeller){
            return "redirect:/detailSeller.html/"+productNo;
        }

        if (isAuctionParticipant) {
            auctionService.endAuction(productNo);
            redirectAttributes.addFlashAttribute("message", "경매에 낙찰되었습니다.");
            System.out.println("check====>");
            return "redirect:/auction/bidSuccess/" + productNo;
        }

        if (isAuctionEnded) {
            redirectAttributes.addFlashAttribute("message", "경매가 종료되었습니다.");
            return "redirect:/detailBuyer/" + productNo;
        }

        return "redirect:/detailBuyer/" + productNo;
    }

    @GetMapping("/bidSuccess/{productNo}")
    public String bidSuccess(@PathVariable("productNo") Long productNo, Model model, HttpSession httpSession){
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        System.out.println("[bidSuccess] > productNo = " + productNo);
        Product product = auctionService.getProductById(productNo);
        model.addAttribute("product", product);
        return "user/product/bidSuccess";
    }

    @GetMapping("/bidFailed/{productNo}")
    public String bidFailed(@PathVariable("productNo") Long productNo){
        return "/user/product/bidFailed";
    }

    /*@GetMapping("/detail/{productNo}")
    public String detailController(Model model, @PathVariable("productNo") Long productNo, HttpSession httpSession) {
        String email = null;
        Long memberNo = null;

        // 세션에서 로그인된 사용자의 이메일을 가져옵니다.
        if (httpSession.getAttribute("member") != null) {
            email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            System.out.println("email = " + email);
            memberNo = auctionService.getMemberByEmail(email).getMemberNo(); // 이메일로 회원 번호를 가져옵니다.
        }

        // 상품 정보를 가져옵니다.
        ProductResDto product = productService.findProductByNo(productNo);

        // 판매자인지 확인합니다.
        boolean isSeller = auctionService.isSeller(memberNo, productNo);

        // 입찰자인지 확인합니다.
        boolean isAuctionParticipant = auctionService.isAuctionParticipant(memberNo, productNo);

        // 경매가 종료되었는지 확인합니다.
        boolean isAuctionEnded = product.getAuctionEndTime().isBefore(LocalDateTime.now());

        // 모델에 필요한 데이터를 추가합니다.
        model.addAttribute("product", product);
        model.addAttribute("isSeller", isSeller);
        model.addAttribute("isAuctionParticipant", isAuctionParticipant);
        model.addAttribute("isAuctionEnded", isAuctionEnded);

        // 판매자일 경우 bidFailed로 리다이렉트
        if (isSeller && product.getSalesStatus().equals(SalesStatus.sale_pause)) {
            return "redirect:/bidFailed";
        }

        // 입찰자일 경우 detailAuction으로 리다이렉트
        if (isAuctionParticipant) {
            return "redirect:/detailAuction/" + productNo;
        }

        // 경매가 종료되었고, 일반 회원 또는 비로그인 사용자
        if (isAuctionEnded) {
            return "user/product/detailBuyer";
        }
        // 그 외 일반 회원 또는 비로그인 사용자
        return "redirect:/detailBuyer/" + productNo;
    }*/
}
