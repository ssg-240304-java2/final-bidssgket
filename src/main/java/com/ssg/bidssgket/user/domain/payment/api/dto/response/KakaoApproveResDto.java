package com.ssg.bidssgket.user.domain.payment.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * 결제 승인 요청 시 사용
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class KakaoApproveResDto {

    private String aid; // 요청 고유 번호
    private String tid; // 결제 고유 번호
    private String cid; // 가맹점 코드

    @JsonProperty("partner_order_id")
    private String partnerOrderId; // 가맹점 주문 번호

    @JsonProperty("partner_user_id")
    private String partnerUserId; // 가맹점 회원 Id

    @JsonProperty("payment_method_type")
    private String paymentMethodType; // 결제 수단, CARD or MONEY
    private Amount amount; // 결제 금액 정보

    @JsonProperty("item_name")
    private String itemName; // 상품명

    @JsonProperty("created_at")
    private String createdAt; // 결제 준비 요청 시각

    @JsonProperty("approved_at")
    private String approvedAt; // 결제 승인 시각

    private String payload; // 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용
}
