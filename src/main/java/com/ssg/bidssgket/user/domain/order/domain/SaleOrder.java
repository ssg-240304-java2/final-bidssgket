package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleOrder extends BaseTimeAndDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int saleOrderNo;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 경매, 즉시구매

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType; // 직거래, 안전거래

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소

    @OneToOne
    @JoinColumn(name = "paymentNo")
    private Payment payment;

    @Builder
    private SaleOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment) {
        this.transactionType = transactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;
        this.payment = payment;
    }

    public static SaleOrder createPurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment) {

        return SaleOrder.builder()
                .transactionType(transactionType)
                .deliveryType(deliveryType)
                .orderStatus(orderStatus)
                .payment(payment)
                .build();
    }

//    @OneToOne
//    @JoinColumn(name = "memberNo")
//    private Member member;
//
//    @OneToOne
//    @JoinColumn(name = "productNo")
//    private Product product;
}