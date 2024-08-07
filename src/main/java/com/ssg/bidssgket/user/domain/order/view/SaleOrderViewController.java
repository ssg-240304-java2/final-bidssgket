package com.ssg.bidssgket.user.domain.order.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/order/sales/")
public class SaleOrderViewController {

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

    /* 판매 내역 */

    @GetMapping("/history/auction")
    public String salesHistoryAuction() { return "/user/order/sales/history/auction"; }

    @GetMapping("/history/progress")
    public String salesHistoryProgress() { return "/user/order/sales/history/progress"; }

    @GetMapping("/history/completed")
    public String salesHistoryCompleted() { return "/user/order/sales/history/completed"; }
}
