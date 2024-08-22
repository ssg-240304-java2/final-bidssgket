package com.ssg.bidssgket.user.domain.payment.api.service;

import com.ssg.bidssgket.user.domain.payment.api.dto.response.KakaoApproveResDto;
import com.ssg.bidssgket.user.domain.payment.api.dto.response.KakaoReadyResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoPayService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

    @Value("${kakao.pay.admin-key}")
    private String adminKey; // profile 설정 파일에서 주입받음

    /***
     * 카카오페이 결제창 연결
     */
    public KakaoReadyResDto kakaoPayReady(String userId, String name, int totalPrice) {

        // 카카오페이 요청 양식
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);                                                     // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", "1234567890");                         // 주문번호
        parameters.put("partner_user_id", userId);                            // 회원 아이디
        parameters.put("item_name", name);                                          // 상품명
        parameters.put("quantity", "1");                                        // 상품 수량
        parameters.put("total_amount", String.valueOf(totalPrice));                                         // 상품 총액
        parameters.put("tax_free_amount", "0");                          // 상품 비과세 금액
        parameters.put("vat_amount", "0");                               // 상품 부가세 금액
        parameters.put("approval_url", "http://localhost:8080/user/payment/success");   // 결제 성공 시 URL
        parameters.put("cancel_url", "http://localhost:8080/user/payment/cancel");      // 결제 취소 시 URL
        parameters.put("fail_url", "http://localhost:8080/user/payment/fail");          // 결제 실패 시 URL

        log.info("[kakaoPayReady] parameters: " + parameters);


        // 파라미터, 헤더
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        // Rest 방식 API를 호출할 수 있는 Spring 내장 클래스
        // Rest API 호출 이후, 응답을 받을 때까지 기다리는 동기 방식 (json, xml 응답)
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

        // RestTemplate의 postForEntity: Post 요청을 보내고 ResponseEntity로 결과를 반환받는 메서드
        ResponseEntity<KakaoReadyResDto> responseEntity = restTemplate.postForEntity(url, requestEntity, KakaoReadyResDto.class);
        log.info("결제 준비 응답객체: " + responseEntity.getBody());

        return responseEntity.getBody();
    }

    /***
     * 카카오페이 결제 승인
     * 사용자가 결제 수단을 선택하고 비밀번호를 입력해 결제 인증을 완료한 뒤,
     * 최종적으로 결제 완료 처리를 하는 단계
     */
    public KakaoApproveResDto approveResponse(String tid, String pgToken, String userId) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid); // 가맹점 코드(테스트용)
        parameters.put("tid", tid); // 결제 고유번호
        parameters.put("partner_order_id", "1234567890"); // 주문 번호
        parameters.put("partner_user_id", userId); // 회원 아이디
        parameters.put("pg_token", pgToken); // 결제승인 요청을 인증하는 토큰

        log.info("[approveResponse] parameters: " + parameters);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";

        try {
            log.info("결제 승인대기중입니다.");
            KakaoApproveResDto kakaoApproveRes = restTemplate.postForObject(url, requestEntity, KakaoApproveResDto.class);
            log.info("결제 승인 응답객체: " + kakaoApproveRes);

            return kakaoApproveRes;
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw e;
        }
    }

    /*
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "SECRET_KEY " + adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }
}