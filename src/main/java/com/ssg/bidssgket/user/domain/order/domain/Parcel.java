package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parcel")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parcel extends BaseTimeAndDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parcelNo; // 택배 번호 [PK]

    @Column(nullable = false)
    private String courierName; // 택배 회사의 이름

    @Column(nullable = false)
    private String trackingNum; // 택배 운송장 번호

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송대기, 배송중, 배송완료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_no", nullable = false)
    private Member seller; // 판매자 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_no", nullable = false)
    private PurchaseOrder purchaseOrder; // 구매 주문서 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_order_no", nullable = false)
    private SaleOrder saleOrder; // 판매 주문서 [FK]

    @Builder
    public Parcel(String courierName, String trackingNum, DeliveryStatus deliveryStatus, Member seller, PurchaseOrder purchaseOrder, SaleOrder saleOrder) {
        this.courierName = courierName;
        this.trackingNum = trackingNum;
        this.deliveryStatus = deliveryStatus;
        this.seller = seller;
        this.purchaseOrder = purchaseOrder;
        this.saleOrder = saleOrder;
    }
}
