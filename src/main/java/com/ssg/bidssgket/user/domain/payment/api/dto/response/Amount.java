package com.ssg.bidssgket.user.domain.payment.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * 결제 금액 정보
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Amount {

    private int total; // 총 결제 금액

    @JsonProperty("tax_free")
    private int taxFree; // 비과세 금액

    private int tax; // 부과세 금액
    private int point; // 사용한 포인트
    private int discount; // 할인 금액

    @JsonProperty("green_deposit")
    private int greenDeposit; // 컵 보증금
}
