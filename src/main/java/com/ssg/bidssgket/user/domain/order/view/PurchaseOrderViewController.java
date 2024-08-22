package com.ssg.bidssgket.user.domain.order.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.PurchaseOrderService;
import com.ssg.bidssgket.user.domain.order.application.dto.response.ProductWithOrderDto;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
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

    private Member getMemberFromSession(HttpSession session) {
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        if (sessionMember == null) {

            return null; //
        }

        String email = sessionMember.getEmail();
        log.info("(SessionMember) Email = {}", email);

        return memberTestService.getMemberByEmail(email);
    }

    private void addPurchaseHistoryAttributes(Model model, Member member) {

        List<Auction> purchaseAuctionItems = purchaseOrderService.getPurchaseAuctionProducts(member.getMemberNo());
        List<ProductWithOrderDto> purchaseTradingItemWithOrder = purchaseOrderService.getPurchaseTradingItemsWithOrder(member.getMemberNo());
        List<Product> purchaseCompletedItems = purchaseOrderService.getPurchaseCompletedProducts(member.getMemberNo());


        log.info("[PurchaseOrderView] purchaseAuctionItems.size: {}", purchaseAuctionItems.size());
        log.info("[PurchaseOrderView] purchaseTradingItems.size: {}", purchaseTradingItemWithOrder.size());
        log.info("[PurchaseOrderView] purchaseCompletedItems.size: {}", purchaseCompletedItems.size());

        model.addAttribute("purchaseAuctionItems", purchaseAuctionItems);
        model.addAttribute("purchaseTradingItems", purchaseTradingItemWithOrder);
        model.addAttribute("purchaseCompletedItems", purchaseCompletedItems);
    }

    @GetMapping("/history/auction")
    public String purchaseHistoryAuction(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);

        if (member == null) {

            return "redirect:/login";
        }

        addPurchaseHistoryAttributes(model, member);

        return "/user/order/purchases/history/auction";
    }

    @GetMapping("/history/progress")
    public String purchaseHistoryProgress(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);
        if (member == null) {
            return "redirect:/login";
        }
        addPurchaseHistoryAttributes(model, member);
        return "/user/order/purchases/history/progress";
    }

    @GetMapping("/history/completed")
    public String purchaseHistoryCompleted(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);
        if (member == null) {
            return "redirect:/login";
        }
        addPurchaseHistoryAttributes(model, member);
        return "/user/order/purchases/history/completed";
    }

    @GetMapping("/pending")
    public String purchasesPending() {
        return "/user/order/purchases/pending";
    }

    @GetMapping("/shipping/{productNo}")
    public String purchasesShipping() {
        return "/user/order/purchases/shipping";
    }

    @GetMapping("/delivered")
    public String purchasesDelivered() {
        return "/user/order/purchases/delivered";
    }

    @GetMapping("/completed")
    public String purchasesCompleted() {
        return "/user/order/purchases/completed";
    }

    @GetMapping("/cancelled")
    public String purchasesCancelled() {
        return "/user/order/purchases/cancelled";
    }
}
