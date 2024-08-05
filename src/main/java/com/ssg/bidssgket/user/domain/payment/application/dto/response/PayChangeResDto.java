package com.ssg.bidssgket.user.domain.payment.application.dto.response;

import com.ssg.bidssgket.user.domain.payment.domain.PayChange;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayChangeResDto {
    private Long payChangeNo;
    private String payChangeType;
    private int payChangeAmount;
    private int balance;
    private LocalDateTime createdAt;

    private Long payNo;

    public PayChangeResDto(PayChange payChange) {
        this.payChangeNo = payChange.getPayChangeNo();
        this.payChangeType = payChange.getPayChangeType().name();
        this.payChangeAmount = payChange.getPayChangeAmount();
        this.balance = payChange.getBalance();
        this.createdAt = payChange.getCreatedAt();

        this.payNo = payChange.getPay().getPayNo();
    }
}