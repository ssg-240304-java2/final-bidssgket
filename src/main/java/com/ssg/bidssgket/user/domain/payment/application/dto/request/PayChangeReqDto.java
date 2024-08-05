package com.ssg.bidssgket.user.domain.payment.application.dto.request;

import com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayChangeReqDto {
    private PayChangeType payChangeType;
    private int payChangeAmount;
    private int initialBalance;

    private Long payNo;

    public PayChangeReqDto(Long payNo, PayChangeType payChangeType, int payChangeAmount) {
        this.payChangeType = payChangeType;
        this.payChangeAmount = payChangeAmount;
        this.initialBalance = payChangeAmount;

        this.payNo = payNo;
    }
}