package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
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
    private Long saleOrderNo; // 판매 주문 번호 [PK]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderTransactionType orderTransactionType; // 경매, 즉시구매

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType; // 직거래, 안전거래

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus; // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 판매자 회원 번호 [FK]

    @OneToOne
    @JoinColumn(name = "productNo", nullable = false)
    private Product product; // 상품 번호 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentNo", nullable = false)
    private Payment payment; // 결제 정보 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelNo")
    private Parcel parcel; // 택배 정보 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryAddressNo")
    private DeliveryAddress deliveryAddress;

    @Builder
    private SaleOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType, OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel, DeliveryAddress deliveryAddress) {
        this.orderTransactionType = orderTransactionType;
        this.deliveryType = deliveryType;
        this.orderStatus = orderStatus;
        this.member = member;
        this.product = product;
        this.payment = payment;
        this.parcel = parcel;
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 판매 주문 생성 메서드
     * @param orderTransactionType 거래 유형
     * @param deliveryType 배송 유형
     * @param orderStatus 주문 상태
     * @param member 판매자
     * @param product 상품
     * @param payment 결제 정보
     * @param parcel 택배 정보
     * @param deliveryAddress 배송지 정보
     * @return 생성된 SaleOrder 객체
     */
    public static SaleOrder addSaleOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType, OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel, DeliveryAddress deliveryAddress) {
        return SaleOrder.builder()
                .orderTransactionType(orderTransactionType)
                .deliveryType(deliveryType)
                .orderStatus(orderStatus)
                .member(member)
                .product(product)
                .payment(payment)
                .parcel(parcel)
                .deliveryAddress(deliveryAddress)
                .build();
    }

    /***
     * 판매자 회원 정보 설정 메서드
     * @param member 판매자 회원 정보
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
        if (payment.getSaleOrder() != this) {
            payment.setSaleOrder(this);
        }
    }

    /***
     * 택배 정보 설정 메서드
     * @param parcel 택배 정보
     */
    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    /***
     * 배송지 정보 설정 메서드
     * @param deliveryAddress
     */
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}