package com.ssg.bidssgket.user.domain.auction.view;

import com.ssg.bidssgket.admin.notification.NotificationService;
import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionReqDto;
import com.ssg.bidssgket.user.domain.auction.domain.dto.AuctionResponseDto;
import com.ssg.bidssgket.user.domain.auction.view.dto.FailedProductReqDto;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.application.MemberService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.SalesStatus;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import com.ssg.bidssgket.user.domain.productwish.domain.dto.MemberDTO;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionViewController {
    private final AuctionService auctionService;
    private final ProductService productService;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;



    /***
     * 경매 등록 폼 불러오기
     * @param model
     * @return
     */
    @GetMapping("/auctionregistform/{productNo}")
    public String showAuctionRegistForm(Model model,  @PathVariable Long productNo, HttpSession httpSession) {
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        MemberDTO member = auctionService.getMemberByEmail(email);
        ProductResDto product = auctionService.getProductById(productNo);
        int minBid = auctionService.getMinBid(productNo);

        AuctionResponseDto auction = auctionService.getAuctionByMemberAndProduct(member.getMemberNo(), productNo);

        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("minBid", minBid);
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
    @PostMapping(value = "/auctionregist/{productNo}", produces = "text/event-stream")
    public String registerAuction(@PathVariable("productNo") Long productNo, @RequestParam(required = false) int minTenderPrice, @RequestParam(required = false) int maxTenderPrice, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        try {
            String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            log.info("회원 정보 >>> {}", email);
            int auctionCount = auctionService.countAuctionsByMemberAndProduct(email, productNo);
            log.info("입찰횟수>>>> {}",auctionCount);
            if (auctionCount >= 2) {
                redirectAttributes.addFlashAttribute("message", "입찰은 최대 2번까지 가능합니다.");
                return "redirect:/detailBuyer/" + productNo;
            }
            AuctionReqDto auctionReqDto = new AuctionReqDto(minTenderPrice, maxTenderPrice, productNo);
            auctionService.registerAuction(auctionReqDto, email);

            /** ===== 알림 시작, 상품 등록 구독 -> 판매자 구독 =====*/

            Long memberNo = null;
            if (httpSession.getAttribute("member") != null) {
                email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
                System.out.println("email = " + email);
                memberNo = memberRepository.findByEmail(email).get().getMemberNo();
            }

            notificationService.subscribeAuction(productNo, memberNo, "bid");

            /** ===== 알림 끝 =====*/

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

        MemberDTO member = auctionService.getMemberByEmail(email);
        ProductResDto product = auctionService.getProductById(productNo);
        AuctionResponseDto auction = auctionService.getAuctionByMemberAndProduct(member.getMemberNo(), productNo);

        int minBid = auction.getMinTenderPrice();
        int maxBid = auction.getMaxTenderPrice();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedMinBid = numberFormat.format(minBid);
        String formattedMaxBid = numberFormat.format(maxBid);



        model.addAttribute("member", member);
        model.addAttribute("product", product);
        /*model.addAttribute("minBid", auction.getMinTenderPrice());
        model.addAttribute("maxBid", auction.getMaxTenderPrice());*/
        model.addAttribute("formattedMinBid", formattedMinBid);
        model.addAttribute("formattedMaxBid", formattedMaxBid);
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

    /***
     * 경매 종료 시 상태 변경
     * @param productNo
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/endAuction/{productNo}")
    public String endAuction(@PathVariable("productNo") Long productNo, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        log.info("productNo =====> {}", productNo);
        String email = null;
        Long memberNo = null;
        if (httpSession.getAttribute("member") != null) {
            email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
            System.out.println("email = " + email);
            memberNo = auctionService.getMemberByEmail(email).getMemberNo();
        }

        ProductResDto product = productService.findProductByNo(productNo);

        boolean isAuctionEnded = product.getAuctionEndTime().isBefore(LocalDateTime.now());
        boolean isSeller = auctionService.isSeller(memberNo, productNo);
        boolean isAuctionParticipant = auctionService.isAuctionParticipant(memberNo, productNo);
        boolean hasBidders = auctionService.hasBidders(productNo);

        System.out.println("isAuctionEnded = " + isAuctionEnded);
        System.out.println("isSeller = " + isSeller);
        System.out.println("isAuctionParticipant = " + isAuctionParticipant);
        System.out.println("hasBidders = " + hasBidders);

        if (isSeller) {
            if (isAuctionEnded) {
                if (!hasBidders) {
                    redirectAttributes.addFlashAttribute("message", "경매가 종료되었으나 입찰자가 없어 판매 중지 상태로 변경되었습니다.");
                    auctionService.deleteIfAuctionExists(productNo);
                    return "redirect:/auction/bidFailed/" + productNo;
                } else {
                    auctionService.endAuction(productNo);
                    return "redirect:/detailSeller/" + productNo;
                }
            }
        }
        if (isAuctionParticipant && isAuctionEnded) {
            boolean isWinningBidder = auctionService.isWinningBidder(memberNo, productNo);
            auctionService.endAuction(productNo);
            if (isWinningBidder) {
                redirectAttributes.addFlashAttribute("message", "경매에 낙찰되었습니다.");
                return "redirect:/auction/bidSuccess/" + productNo;
            } else {
                redirectAttributes.addFlashAttribute("message", "경매에 낙찰되지 않았습니다.");
                return "redirect:/detailAuction/"+productNo;
            }
        }

        if (isAuctionEnded) {
            auctionService.endAuction(productNo);
            redirectAttributes.addFlashAttribute("message", "경매가 종료되었습니다.");
            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping("/checkStatus/{productNo}")
    @ResponseBody
    public String checkAuctionStatus(@PathVariable("productNo") Long productNo) {
        ProductResDto product = productService.findProductByNo(productNo);
        return product.getSalesStatus().toString();
    }
    /***
     * 낙찰자가 보는 페이지
     * @param productNo
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/bidSuccess/{productNo}")
    public String bidSuccess(@PathVariable("productNo") Long productNo, Model model, HttpSession httpSession){
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        ProductResDto product = productService.findProductByNo(productNo);
        List<AuctionResponseDto> auction = auctionService.findByProductNo(productNo);
        Optional<Member> memberInfo = memberRepository.findByEmail(email);
        Long memberNo = memberInfo.get().getMemberNo();

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("product", product);
        model.addAttribute("auction", auction);
        return "user/product/bidSuccess";
    }

    /***
     * 낙찰자의 낙찰 포기
     * @param productNo
     * @return
     */
    @PostMapping("/abandonBid/{productNo}")
    public String abandonBid(@PathVariable("productNo") Long productNo, HttpSession httpSession,  RedirectAttributes redirectAttributes){
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        auctionService.abandonBid(productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        System.out.println("product.getSalesStatus() = " + product.getSalesStatus());

        return "redirect:/";
    }

    /***
     * 낙찰자의 주문 - 준영님 페이지 연결
     * @param productNo
     * @return
     */
    @PostMapping("/confirmPurchase/{productNo}")
    public String confirmPurchase(@PathVariable("productNo") Long productNo){
        return "user/order/purchases/pending";
    }

    /***
     * 유찰된 상품에 대한 판매자 페이지
     * @param productNo
     * @return
     */
    @GetMapping("/bidFailed/{productNo}")
    public String bidFailed(@PathVariable("productNo") Long productNo, Model model, HttpSession httpSession){
        System.out.println("유찰된 상품");
        String email = ((SessionMember) httpSession.getAttribute("member")).getEmail();
        ProductResDto product = productService.findProductByNo(productNo);
        List<AuctionResponseDto> auction = auctionService.findByProductNo(productNo);
//        List<Auction> auction = productService.findAuctionByProductNo(productNo);
//        Optional<Member> memberInfo = memberRepository.findByEmail(email);
        MemberDTO memberInfo = auctionService.getMemberByEmail(email);
        Long memberNo = memberInfo.getMemberNo();

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("product", product);
        model.addAttribute("auction", auction);
        return "user/product/bidFailed";
    }

    /**
     * 경매 재개 (update) 페이지로 이동하면서 판매 상태를 selling으로 변경
     */
    @GetMapping("/update/{productNo}")
    public String updateAuction(@PathVariable("productNo") Long productNo, RedirectAttributes redirectAttributes) {
        try {
            productService.changeProductSalesStatus(productNo, SalesStatus.selling);
            return "redirect:/auction/bidfailedupdate/" + productNo;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "경매 재개에 실패했습니다.");
            return "redirect:/auction/bidFailed/" + productNo;
        }
    }

    /**
     * 상품 삭제
     */
    @PostMapping("/failbiddelete/{productNo}")
    public String deleteProduct(@PathVariable("productNo") Long productNo, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductByNo(productNo);
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 삭제되었습니다.");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "상품 삭제에 실패했습니다.");
            return "redirect:/auction/bidFailed/" + productNo;
        }
    }

    @GetMapping("/bidfailedupdate/{productNo}")
    public String failedProductUpdate(@PathVariable("productNo") Long productNo, Model model){
        ProductResDto product = productService.findProductByNo(productNo);
        model.addAttribute("product", product);
        return "user/auction/bidfailedupdate";
    }

    @PostMapping("/bidfailedupdate/{productNo}")
    public String failedBidProductUpdate(@PathVariable("productNo") Long productNo, @ModelAttribute FailedProductReqDto failedProductReqDto) {
        productService.updateFailedBidProduct(failedProductReqDto);
        return "redirect:/detailSeller/"+ productNo;
    }

}
