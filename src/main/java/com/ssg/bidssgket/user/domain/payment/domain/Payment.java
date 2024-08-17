package com.ssg.bidssgket.user.domain.payment.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeAndDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentNo; // 결제 번호 [PK]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType; // 카카오페이, 비스킷페이

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; // 입금, 출금

    @Column(nullable = false)
    private int amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus; // 결제완료, 결제취소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 결제와 관련된 회원 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payChangeNo", nullable = false)
    private PayChange payChange; // 비스킷 페이 잔액 변경 내역 [FK]

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private PurchaseOrder purchaseOrder; // 구매 주문서 [FK]

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private SaleOrder saleOrder; // 판매 주문서 [FK]

    @Builder
    public Payment(PaymentType paymentType, TransactionType transactionType, int amount, PaymentStatus paymentStatus, Member member, PayChange payChange) {
        this.paymentType = paymentType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.member = member;
        this.payChange = payChange;
    }

    public static Payment addpayment(PaymentType paymentType, TransactionType transactionType, int amount, PaymentStatus paymentStatus, Member member, PayChange payChange) {
        return Payment.builder()
                .paymentType(paymentType)
                .transactionType(transactionType)
                .amount(amount)
                .paymentStatus(paymentStatus)
                .member(member)
                .payChange(payChange)
                .build();
    }

    /***
     * 회원 정보 설정 메서드
     * @param member 회원 정보
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /***
     * 비스킷 페이 잔액 변경 내역 설정 메서드
     * @param payChange
     */
    public void setPayChange(PayChange payChange) {
        this.payChange = payChange;
    }

    /***
     * 구매 주문서 설정 메서드
     * @param purchaseOrder 구매 주문서 객체
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        if (purchaseOrder.getPayment() != this) {
            purchaseOrder.setPayment(this);
        }
    }

    /***
     * 판매 주문서 설정 메서드
     * @param saleOrder 판매 주문서 객체
     */
    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
        if (saleOrder.getPayment() != this) {
            saleOrder.setPayment(this);
        }
    }
}