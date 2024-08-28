package com.ssg.bidssgket.user.domain.order.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.PurchaseOrderService;
import com.ssg.bidssgket.user.domain.order.application.SaleOrderService;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.payment.application.dto.response.PayResDto;
import com.ssg.bidssgket.user.domain.payment.application.service.PayService;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class OrderViewController {

    private final MemberTestService memberTestService;
    private final PayService payService;
    private final PurchaseOrderService purchaseOrderService;
    private final SaleOrderService saleOrderService;

    public OrderViewController(MemberTestService memberTestService, PayService payService, PurchaseOrderService purchaseOrderService, SaleOrderService saleOrderService) {
        this.memberTestService = memberTestService;
        this.payService = payService;
        this.purchaseOrderService = purchaseOrderService;
        this.saleOrderService = saleOrderService;
    }

    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {  // 비스킷 온도 동적으로 변경
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        if (sessionMember == null) {
            return "redirect:/login";
        }

        // 세션 정보를 통해 데이터베이스에서 회원 정보 가져오기
        Member member = memberTestService.getMemberByEmail(sessionMember.getEmail());
        if (member == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 회원의 페이 조회
        Pay pay = payService.getOrCreatePay(member);
        log.info("(Member) member Pay : {}", pay);

        model.addAttribute("biscuit", member.getBiscuit());
        model.addAttribute("memberNickname", member.getMemberNickname());
        model.addAttribute("pay", new PayResDto(pay));

        // 구매 상품 조회
        List<Auction> purchaseAuctionItems = purchaseOrderService.getPurchaseAuctionProducts(member.getMemberNo());
        List<Product> purchaseTradingItems = purchaseOrderService.getPurchaseTradingProducts(member.getMemberNo());
        List<Product> purchaseCompletedItems = purchaseOrderService.getPurchaseCompletedProducts(member.getMemberNo());

        log.info("[PurchaseOrderView] purchaseAuctionItems.size: {}", purchaseAuctionItems.size());
        log.info("[PurchaseOrderView] purchaseTradingItems.size: {}", purchaseTradingItems.size());
        log.info("[PurchaseOrderView] purchaseCompletedItems.size: {}", purchaseCompletedItems.size());

        model.addAttribute("purchaseAuctionItems", purchaseAuctionItems);
        model.addAttribute("purchaseTradingItems", purchaseTradingItems);
        model.addAttribute("purchaseCompletedItems", purchaseCompletedItems);

        // 판매 상품 조회
        List<Product> saleAuctionItems = saleOrderService.getSaleAuctionProducts(member.getMemberNo());
        List<Product> saleTradingItems = saleOrderService.getSaleTradingProducts(member.getMemberNo());
        List<Product> saleCompletedItems = saleOrderService.getSaleCompletedProducts(member.getMemberNo());

        log.info("[SaleOrderView] saleAuctionItems.size: {}", saleAuctionItems.size());
        log.info("[SaleOrderView] saleTradingItems.size: {}", saleTradingItems.size());
        log.info("[SaleOrderView] saleCompletedItems.size: {}", saleCompletedItems.size());

        model.addAttribute("saleAuctionItems", saleAuctionItems);
        model.addAttribute("saleTradingItems", saleTradingItems);
        model.addAttribute("saleCompletedItems", saleCompletedItems);

        return "/user/order/mypage"; }

    @GetMapping("/order/checkout")
    public String productCheckout() { return "/user/order/checkout"; }
}