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
    private Long purchaseOrderNo; // 구매 주문 번호 [PK]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; // 경매, 즉시구매

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType; // 직거래, 안전거래

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus; // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소

    @ManyToOne
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 구매자 회원 번호 [FK]

    @OneToOne
    @JoinColumn(name = "productNo", nullable = false)
    private Product product; // 상품 번호 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentNo", nullable = false)
    private Payment payment; // 결제 정보 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelNo")
    private Parcel parcel; // 택배 정보 [FK]

    @Builder
    private PurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel) {
        this.transactionType = transactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;
        this.member = member;
        this.product = product;
        this.payment = payment;
        this.parcel = parcel;
    }

    /**
     * 구매 주문 생성 메서드
     * @param transactionType 거래 유형
     * @param deliveryType 배송 유형
     * @param orderStatus 주문 상태
     * @param member 구매자
     * @param product 상품
     * @param payment 결제 정보
     * @param parcel 택배 정보
     * @return 생성된 PurchaseOrder 객체
     */
    public static PurchaseOrder addPurchaseOrder(TransactionType transactionType, DeliveryType deliveryType, OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel) {
        return PurchaseOrder.builder()
                .transactionType(transactionType)
                .deliveryType(deliveryType)
                .orderStatus(orderStatus)
                .member(member)
                .product(product)
                .payment(payment)
                .parcel(parcel)
                .build();
    }

    /***
     * 구매자 회원 정보 설정 메서드
     * @param member 구매자 회원 정보
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /***
     * 상품 정보 설정 메서드
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /***
     * 결제 정보 설정 메서드
     * @param payment 결제 정보
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment.getPurchaseOrder() != this) {
            payment.setPurchaseOrder(this);
        }
    }

    /***
     * 택배 정보 설정 메서드
     * @param parcel 택배 정보
     */
    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }
}