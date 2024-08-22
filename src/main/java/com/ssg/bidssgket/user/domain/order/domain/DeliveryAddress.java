package com.ssg.bidssgket.user.domain.order.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveryAddress")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress extends BaseTimeAndDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryAddressNo; // 배송지 번호 [PK]

    @Column(nullable = false)
    private String receiverName; // 받는 분

    @Column(nullable = false)
    private String contactNumber; // 연락처

    @Column(nullable = false)
    private String postcode; // 우편번호

    @Column(nullable = false)
    private String deliveryAddress; // 배송지 주소

    @Column(nullable = false)
    private String detailAddress; // 상세 주소

    @Column(nullable = false)
    private String deliveryRequest; // 배송 요청 사항

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 구매자 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productNo", nullable = false)
    private Product product; // 상품 [FK]

    @OneToOne(mappedBy = "deliveryAddress", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private PurchaseOrder purchaseOrder; // 구매 주문서

    @OneToOne(mappedBy = "deliveryAddress", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private SaleOrder saleOrder; // 판매 주문서

    @Builder
    public DeliveryAddress(String receiverName, String contactNumber, String postcode, String deliveryAddress, String detailAddress, String deliveryRequest, Member member, Product product) {
        this.receiverName = receiverName;
        this.contactNumber = contactNumber;
        this.postcode = postcode;
        this.deliveryAddress = deliveryAddress;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;
        this.member = member;
        this.product = product;
    }

    public static DeliveryAddress addDeliveryAddress(Member member, Product product, String receiverName, String contactNumber, String postcode, String deliveryAddress, String detailAddress, String deliveryRequest) {

        return DeliveryAddress.builder()
                .member(member)
                .product(product)
                .receiverName(receiverName)
                .contactNumber(contactNumber)
                .postcode(postcode)
                .deliveryAddress(deliveryAddress)
                .detailAddress(detailAddress)
                .deliveryRequest(deliveryRequest)
                .build();
    }

    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setDeliveryAddress(this);
        this.purchaseOrder = purchaseOrder;
    }

    public void addSaleOrder(SaleOrder saleOrder) {
        saleOrder.setDeliveryAddress(this);
        this.saleOrder = saleOrder;
    }

    /***
     * 구매자 회원 정보 설정 메서드
     * @param member 구매자 회원 정보
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /***
     * 상품 설정 메서드
     * @param product 상품 정보
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /***
     * 구매 주문서 설정 메서드
     * @param purchaseOrder
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    /***
     * 판매 주문서 설정 메서드
     * @param saleOrder
     */
    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }
}