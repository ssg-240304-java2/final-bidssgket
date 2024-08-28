package com.ssg.bidssgket.user.domain.order.view;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.SaleOrderService;
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
@RequestMapping("/user/order/sales/")
public class SaleOrderViewController {

    private final MemberTestService memberTestService;
    private final SaleOrderService saleOrderService;

    public SaleOrderViewController(MemberTestService memberTestService, SaleOrderService saleOrderService) {
        this.memberTestService = memberTestService;
        this.saleOrderService = saleOrderService;
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

    private void addSaleHistoryAttributes(Model model, Member member) {

        List<Product> saleAuctionItems = saleOrderService.getSaleAuctionProducts(member.getMemberNo());
        List<Product> saleTradingItems = saleOrderService.getSaleTradingProducts(member.getMemberNo());
        List<Product> saleCompletedItems = saleOrderService.getSaleCompletedProducts(member.getMemberNo());

        log.info("[SaleOrderView] saleAuctionItems.size: {}", saleAuctionItems.size());
        log.info("[SaleOrderView] saleTradingItems.size: {}", saleTradingItems.size());
        log.info("[SaleOrderView] saleCompletedItems.size: {}", saleCompletedItems.size());

        model.addAttribute("saleAuctionItems", saleAuctionItems);
        model.addAttribute("saleTradingItems", saleTradingItems);
        model.addAttribute("saleCompletedItems", saleCompletedItems);
    }

    /* 판매 내역 */

    @GetMapping("/history/auction")
    public String salesHistoryAuction(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);

        if (member == null) {

            return "redirect:/login";
        }

        addSaleHistoryAttributes(model, member);

        return "/user/order/sales/history/auction";
    }

    @GetMapping("/history/progress")
    public String salesHistoryProgress(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);

        if (member == null) {

            return "redirect:/login";
        }

        addSaleHistoryAttributes(model, member);

        return "/user/order/sales/history/progress";
    }

    @GetMapping("/history/completed")
    public String salesHistoryCompleted(Model model, HttpSession session) {
        Member member = getMemberFromSession(session);

        if (member == null) {

            return "redirect:/login";
        }

        addSaleHistoryAttributes(model, member);

        return "/user/order/sales/history/completed";
    }

    /* 판매 주문서 */

    @GetMapping("/pending")
    public String salesPending() { return "/user/order/sales/pending"; }

    @GetMapping("/shipping")
    public String salesShipping() { return "/user/order/sales/shipping"; }

    @GetMapping("/delivered")
    public String salesDelivered() { return "/user/order/sales/delivered"; }

    @GetMapping("/completed")
    public String salesCompleted() { return "/user/order/sales/completed"; }

    @GetMapping("/cancelled")
    public String salesCancelled() { return "/user/order/sales/cancelled"; }
}
