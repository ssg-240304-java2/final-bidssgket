package com.ssg.bidssgket.admin.view.admin.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderViewController {


    /**
     * 구매 주문 목록 조회 페이지
     */
    @GetMapping("/purchase/list")
    public String purchaseOrderListPage() {
        log.info("===== purchase order list page =====");

        return "admin/content/pages/order/purchase-order-list";
    }

    /**
     * 판매 주문 목록 조회 페이지
     */
    @GetMapping("/sale/list")
    public String salesOrderListPage() {
        log.info("===== sale order list page =====");

        return "admin/content/pages/order/sale-order-list";
    }


    /**
     * 주문 상세 조회 페이지
     */
    @GetMapping("/sale/info/{}")
    public String saleOrderInfoPage() {
        log.info("===== user list page =====");

//        return "admin/content/pages/user/user-list";
        return null;
    }

    /**
     * 주문 상세 조회 페이지
     */
    @GetMapping("/purchase/info/{}")
    public String purchaseOrderInfoPage() {
        log.info("===== user list page =====");

//        return "admin/content/pages/user/user-list";
        return null;
    }






}
