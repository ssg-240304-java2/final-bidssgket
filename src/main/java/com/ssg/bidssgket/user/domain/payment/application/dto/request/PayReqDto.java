package com.ssg.bidssgket.user.domain.payment.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayReqDto {
    private int payBalance;

    private Long memberNo;

    public PayReqDto(Long memberNo, int payBalance) {
        this.payBalance = payBalance;

        this.memberNo = memberNo;
    }
}