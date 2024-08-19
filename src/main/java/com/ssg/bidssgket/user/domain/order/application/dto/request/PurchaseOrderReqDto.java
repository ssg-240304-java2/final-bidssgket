package com.ssg.bidssgket.user.domain.order.application.dto.request;

import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrderReqDto {
    private OrderTransactionType orderTransactionType;
    private DeliveryType deliveryType;
    private OrderStatus orderStatus;

    private Long buyerNo;
    private Long productNo;
    private Long paymentNo;

    public PurchaseOrderReqDto(OrderTransactionType orderTransactionType, DeliveryType deliveryType, OrderStatus orderStatus, Long buyerNo, Long productNo, Long paymentNo) {
        this.orderTransactionType = orderTransactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;

        this.buyerNo = buyerNo;
        this.productNo = productNo;
        this.paymentNo = paymentNo;
    }
}
