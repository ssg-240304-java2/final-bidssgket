package com.ssg.bidssgket.user.domain.payment.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
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
    private int paymentNo;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 카카오페이, 비스킷페이

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 충전, 출금

    @Column(nullable = false)
    private int amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 결제완료, 결제취소

    @OneToOne(mappedBy = "payment")
    private PurchaseOrder purchaseOrder;

//    @OneToOne
//    @JoinColumn(name = "memberNo")
//    private Member memberNo;

}
