package com.ssg.bidssgket.user.domain.order.application.dto.response;

import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleOrderResDto {
    private Long saleOrderNo;
    private TransactionType transactionType;
    private DeliveryType deliveryType;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    private Long memberNo;
    private Long productNo;
    private Long paymentNo;

    public SaleOrderResDto(SaleOrder saleOrder) {
        this.saleOrderNo = saleOrder.getSaleOrderNo();
        this.transactionType = saleOrder.getTransactionType();
        this.deliveryType = saleOrder.getDeliveryType();
        this.orderStatus = saleOrder.getOrderStatus();
        this.createdAt = saleOrder.getCreatedAt();
        this.updatedAt = saleOrder.getUpdatedAt();
        this.isDeleted = saleOrder.getIsDeleted();

        this.memberNo = saleOrder.getMember().getMemberNo();
        this.productNo = saleOrder.getProduct().getProductNo();
        this.paymentNo = saleOrder.getPayment().getPaymentNo();
    }
}