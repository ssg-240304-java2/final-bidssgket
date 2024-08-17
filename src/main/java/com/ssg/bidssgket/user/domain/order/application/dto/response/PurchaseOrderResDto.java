package com.ssg.bidssgket.user.domain.order.application.dto.response;

import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrderResDto {
    private Long purchaseOrderNo;
    private TransactionType transactionType;
    private DeliveryType deliveryType;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    private Long memberNo;
    private Long productNo;
    private Long paymentNo;

    public PurchaseOrderResDto(PurchaseOrder purchaseOrder) {
        this.purchaseOrderNo = purchaseOrder.getPurchaseOrderNo();
        this.transactionType = purchaseOrder.getTransactionType();
        this.deliveryType = purchaseOrder.getDeliveryType();
        this.orderStatus = purchaseOrder.getOrderStatus();
        this.createdAt = purchaseOrder.getCreatedAt();
        this.updatedAt = purchaseOrder.getUpdatedAt();
        this.isDeleted = purchaseOrder.getIsDeleted();

        this.memberNo = purchaseOrder.getMember().getMemberNo();
        this.productNo = purchaseOrder.getProduct().getProductNo();
        this.paymentNo = purchaseOrder.getPayment().getPaymentNo();
    }
}