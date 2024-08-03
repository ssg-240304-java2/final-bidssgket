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
    private int paymentNo; // 결제 번호 [PK]

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 카카오페이, 비스킷페이

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 입금, 출금

    @Column(nullable = false)
    private int amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 결제완료, 결제취소

    @OneToOne(mappedBy = "payment")
    private PurchaseOrder purchaseOrder; // 구매 주문서 [FK]

    @OneToOne(mappedBy = "payment")
    private SaleOrder saleOrder; // 판매 주문서 [FK]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member; // 결제와 관련된 회원 [FK]

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_change_no")
    private PayChange payChange; // 비스킷 페이 잔액 변경 내역 [FK]

    @Builder
    public Payment(PaymentType paymentType, TransactionType transactionType, int amount, PaymentStatus paymentStatus, Member member, PayChange payChange, PurchaseOrder purchaseOrder, SaleOrder saleOrder) {
        this.paymentType = paymentType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.member = member;
        this.payChange = payChange;
        this.purchaseOrder = purchaseOrder;
        this.saleOrder = saleOrder;
    }
}

