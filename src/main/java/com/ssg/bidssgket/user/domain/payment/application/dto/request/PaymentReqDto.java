package com.ssg.bidssgket.user.domain.payment.application.dto.request;

import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentReqDto {
    private PaymentType paymentType;
    private TransactionType transactionType;
    private int amount;
    private PaymentStatus paymentStatus;

    private Long memberNo;

    public PaymentReqDto(Long memberNo, PaymentType paymentType, TransactionType transactionType, int amount, PaymentStatus paymentStatus) {
        this.paymentType = paymentType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;

        this.memberNo = memberNo;
    }
}