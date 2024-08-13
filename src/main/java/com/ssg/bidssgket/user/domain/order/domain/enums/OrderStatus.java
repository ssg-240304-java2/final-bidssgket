package com.ssg.bidssgket.user.domain.order.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소
    PENDING_PAYMENT("결제대기"),
    PAID("결제완료"),
    PENDING_SHIPMENT("배송대기"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료"),
    COMPLETED("주문완료"),
    PAYMENT_CANCELLED("결제취소"),
    ORDER_CANCELLED("주문취소");

    private String val;

    OrderStatus(String val) {
        this.val = val;
    }
}
