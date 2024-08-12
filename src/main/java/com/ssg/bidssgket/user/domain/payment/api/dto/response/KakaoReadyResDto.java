package com.ssg.bidssgket.user.domain.payment.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * 결제 요청 시 카카오게 전달 받음
 */
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class KakaoReadyResDto {
    @JsonProperty("tid")
    private String tid; // 결제 고유 번호

    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl; // 모바일 웹일 경우 받는 결제 페이지 url

    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl; // pc 웹일 경우 받는 결제 페이지

    @JsonProperty("created_at")
    private String createdAt;
}