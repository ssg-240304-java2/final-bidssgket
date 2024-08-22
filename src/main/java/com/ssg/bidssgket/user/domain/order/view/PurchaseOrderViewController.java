package com.ssg.bidssgket.user.domain.order.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.OrderService;
import com.ssg.bidssgket.user.domain.order.application.PurchaseOrderService;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/order/purchases")
public class PurchaseOrderViewController {

    private final MemberTestService memberTestService;
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderViewController(MemberTestService memberTestService, PurchaseOrderService purchaseOrderService) {
        this.memberTestService = memberTestService;
        this.purchaseOrderService = purchaseOrderService;
    }

    /* 구매 주문서 */

    @GetMapping("/pending")
    public String purchasesPending() { return "/user/order/purchases/pending"; }

    @GetMapping("/shipping")
    public String purchasesShipping() { return "/user/order/purchases/shipping"; }

    @GetMapping("/delivered")
    public String purchasesDelivered() { return "/user/order/purchases/delivered"; }

    @GetMapping("/completed")
    public String purchasesCompleted() { return "/user/order/purchases/completed"; }

    @GetMapping("/cancelled")
    public String purchasesCancelled() { return "/user/order/purchases/cancelled"; }

    /* 구매 내역 */

    @GetMapping("/history/auction")
    public String getAuctionItems(Model model, HttpSession session) {

        // 1. 세션에서 회원 정보 가져오기
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        if (sessionMember == null) {

            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 2. 세션 정보를 통해 데이터 베이스에서 회원 정보 가져오기
        String email = sessionMember.getEmail();
        log.info("(SessionMember) Email = {}", email);

        Member member = memberTestService.getMemberByEmail(email);
        log.info("(Member) Member = {}", member);

        // 3. 회원 번호를 통해 경매중인 상품 목록 조회
        List<Auction> auctionItems = purchaseOrderService.getPurchaseAuctionProducts(member.getMemberNo());
        log.info("[PurchaseOrderView] auctionItems: {}", auctionItems);

        // 4. 회원 번호를 통해 구매중인 상품 목록 조회
//        List<Product> tradingItems = purchaseOrderService.getPurchaseTradingProducts(member.getMemberNo());

        // 5. 회원 번호를 통해 구매완료된 상품 목록 조회
//        List<Product>

        // 4. 모델에 데이터 추가
        model.addAttribute("auctionItems", auctionItems);

        return "/user/order/purchases/history/auction";
    }

    @GetMapping("/history/progress")
    public String purchaseHistoryProgress() { return "/user/order/purchases/history/progress"; }

    @GetMapping("/history/completed")
    public String purchaseHistoryCompleted() { return "/user/order/purchases/history/completed"; }
}
