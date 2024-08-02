package com.ssg.bidssgket.user.domain.order.domain.enums;

public enum OrderStatus {

    // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소
    PENDING_PAYMENT, PAID, PENDING_SHIPMENT, IN_TRANSIT, DELIVERED, COMPLETED, PAYMENT_CANCELLED, ORDER_CANCELLED
}
