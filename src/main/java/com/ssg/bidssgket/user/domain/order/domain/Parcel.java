package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

//    @OneToOne
//    @JoinColumn(name = "sellerNo")
//    private Member sellerNo; // 판매자

    @OneToOne
    @JoinColumn(name = "purchaseOrderNo")
    private PurchaseOrder purchaseOrderNo; // 구매 주문서

    @OneToOne
    @JoinColumn(name= "saleOrderNo")
    private SaleOrder saleOrderNo; // 판매 주문서
}
