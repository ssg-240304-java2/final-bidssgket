package com.ssg.bidssgket.user.domain.order.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/order/purchases")
public class PurchaseOrderViewController {

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
    public String purchasesHistoryAuction() { return "/user/order/purchases/history/auction"; }

    @GetMapping("/history/progress")
    public String purchaseHistoryProgress() { return "/user/order/purchases/history/progress"; }

    @GetMapping("/history/completed")
    public String purchaseHistoryCompleted() { return "/user/order/purchases/history/completed"; }
}
