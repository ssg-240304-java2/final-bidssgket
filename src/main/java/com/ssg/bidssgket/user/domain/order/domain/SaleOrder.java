package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
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
    private int saleOrderNo; // 판매 주문 번호 [PK]

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
    @JoinColumn(name = "seller_no")
    private Member seller; // 판매자 회원 번호 [FK]

    @OneToOne
    @JoinColumn(name = "product_no")
    private Product product; // 상품 번호 [FK]

    @Builder
    private SaleOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment, Member seller, Product product) {
        this.transactionType = transactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;
        this.payment = payment;
        this.seller = seller;
        this.product = product;
    }

    public static SaleOrder createPurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Payment payment, Member seller, Product product) {

        return SaleOrder.builder()
                .transactionType(transactionType)
                .deliveryType(deliveryType)
                .orderStatus(orderStatus)
                .payment(payment)
                .seller(seller)
                .product(product)
                .build();
    }
}