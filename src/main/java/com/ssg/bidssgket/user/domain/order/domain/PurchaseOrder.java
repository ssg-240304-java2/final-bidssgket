package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrder extends BaseTimeAndDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseOrderNo; // 구매 주문 번호 [PK]

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 경매, 즉시구매

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType; // 직거래, 안전거래

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소

    @OneToOne
    @JoinColumn(name = "payment_no")
    private Payment payment; // 결제 번호 [FK]

    @ManyToOne
    @JoinColumn(name = "buyer_no")
    private Member buyer; // 구매자 회원 번호 [FK]

    @OneToOne
    @JoinColumn(name = "product_no")
    private Product product; // 상품 번호 [FK]

    @Builder
    private PurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment, Member buyer, Product product) {
        this.transactionType = transactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;
        this.payment = payment;
        this.buyer = buyer;
        this.product = product;
    }

    public static PurchaseOrder createPurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment, Member buyer, Product product) {

        return PurchaseOrder.builder()
                .transactionType(transactionType)
                .deliveryType(deliveryType)
                .orderStatus(orderStatus)
                .payment(payment)
                .buyer(buyer)
                .product(product)
                .build();
    }
}