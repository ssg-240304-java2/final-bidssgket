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
    private Long parcelNo; // 택배 번호 [PK]

    @Column(nullable = false)
    private String courierName; // 택배 회사의 이름

    @Column(nullable = false)
    private String trackingNum; // 택배 운송장 번호

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송대기, 배송중, 배송완료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 판매자 [FK]

    @OneToOne(mappedBy = "parcel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private PurchaseOrder purchaseOrder; // 구매 주문서 [FK]

    @OneToOne(mappedBy = "parcel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private SaleOrder saleOrder; // 판매 주문서 [FK]

    @Builder
    public Parcel(String courierName, String trackingNum, DeliveryStatus deliveryStatus, Member member) {
        this.courierName = courierName;
        this.trackingNum = trackingNum;
        this.deliveryStatus = deliveryStatus;
        this.member = member;
    }

    /***
     * 택배 정보 생성 메서드
     * @param courierName 택배사 이름
     * @param trackingNum 운소장 번호
     * @param deliveryStatus 배송 상태
     * @param member 판매자 정보
     * @return
     */
    public static Parcel addParcel(String courierName, String trackingNum, DeliveryStatus deliveryStatus, Member member) {
        return Parcel.builder()
                .courierName(courierName)
                .trackingNum(trackingNum)
                .deliveryStatus(deliveryStatus)
                .member(member)
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
     * 구매 주문서 설정 메서드
     * @param purchaseOrder 구매 주문서 정보
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    /***
     * 판매 주문서 설정 메서드
     * @param saleOrder 판매 주문서 정보
     */
    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }
}