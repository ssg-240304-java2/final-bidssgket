package com.ssg.bidssgket.user.domain.payment.api.controller;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.dto.request.PaymentCreateForm;
import com.ssg.bidssgket.user.domain.payment.api.dto.response.KakaoReadyResDto;
import com.ssg.bidssgket.user.domain.payment.api.service.KakaoPayService;
import com.ssg.bidssgket.user.domain.payment.api.dto.response.KakaoApproveResDto;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.payment.application.dto.response.ErrorResponse;
import com.ssg.bidssgket.user.domain.payment.application.service.PayChangeService;
import com.ssg.bidssgket.user.domain.payment.application.service.PayService;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.PayChange;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType;
import com.ssg.bidssgket.user.domain.payment.exception.BusinessLogicException;
import com.ssg.bidssgket.user.domain.payment.exception.ExceptionCode;
import com.ssg.bidssgket.user.util.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
@Slf4j
public class KakaoPayApiController {

    private final KakaoPayService kakaoPayService;
    private final PayService payService;
    private final PayChangeService payChangeService;

    private final MemberTestService memberTestService;

    /***
     * 결제 요청
     */
    @PostMapping(value = "/ready")
    public @ResponseBody KakaoReadyResDto readyToKakaoPay(@RequestBody PaymentCreateForm paymentCreateForm) {

        String name = paymentCreateForm.getName();
        int totalPrice = paymentCreateForm.getTotalPrice();

        log.info("주문 상품 이름: " + name);
        log.info("주문 금액: " + totalPrice);

        // 카카오 결제 준비하기
        KakaoReadyResDto kakaoReadyRes = kakaoPayService.kakaoPayReady(name, totalPrice);

        // Session에 결제 고유번호(tid) 저장하기
        SessionUtils.addAttribute("tid", kakaoReadyRes.getTid());
        log.info("결제 고유번호: " + kakaoReadyRes.getTid());

        return kakaoReadyRes;
    }

    /***
     * 결제 성공
     */
//    @GetMapping("/success2")
//    public String payCompleted(@RequestParam("pg_token") String pgToken) {
//
//        String tid = SessionUtils.getStringAttributeValue("tid");
//        log.info("[payCompleted] 결제승인 요청을 인증하는 토큰: " + pgToken);
//        log.info("[payCompleted] 결제 고유번호: " + tid);
//
//        // 카카오 결제 요청하기
//        KakaoApproveResDto kakaoApproveRes = kakaoPayService.approveResponse(tid, pgToken);
//
//        return "redirect:/user/mypage";
//    }

    @GetMapping("/success")
    public ResponseEntity<?> afterGetRedirectUrl(@RequestParam("pg_token") String pgToken) {


        String tid = SessionUtils.getStringAttributeValue("tid");
        log.info("[payCompleted] 결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("[payCompleted] 결제 고유번호: " + tid);

        try {
            // 1. 주어진 거래 ID와 pg_token을 사용하여 카카오 결제를 승인 시도
            KakaoApproveResDto kakaoApproveRes = kakaoPayService.approveResponse(tid, pgToken);

            // 2. 회원 정보 조회
            Long MemberNo = 1L;
            Member member = memberTestService.getMember(MemberNo);

            // 3. 회원의 Pay 정보 조회 또는 생성
            Pay pay = payService.getOrCreatePay(member);

            // 4. Pay 정보를 PayChangeService로 이동
            int initialBalance = pay.getPayBalance(); // 현재 잔액 조회
            int payChangeAmount = kakaoApproveRes.getAmount().getTotal(); // 결제 승인된 금액

            // 5. PayChangeService에 초기 잔액, 변동 잔액, 업데이트 이후 잔액 저장
            PayChangeType payChangeType = determinePayChangeType(kakaoApproveRes);
            PayChange payChange = payChangeService.savePayChange(payChangeType, payChangeAmount, initialBalance, pay);

            // 6. Update 이후 잔액을 Pay 정보에 저장
            pay.setPayBalance(payChange.getUpdatedBalance());
            payService.updatePay(pay);

            // 승인 성공 시, kakaoApproveResDto와 함께 상태 OK를 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(kakaoApproveRes);
        } catch (BusinessLogicException e) {

            // 비즈니스 로직 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {

            // 에러 로그를 기록
            log.error("카카오 결제 승인 중 오류 발생", e);

            // INTERNAL_SERVER_ERROR 상태와 예외 상세 내용을 포함한 응답을 반환
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    /***
     * kakaoApproveResDto로부터 PayChangeType을 결정하는 로직을 구현합니다.
     * @return 입금 or 출금
     */
    private PayChangeType determinePayChangeType(KakaoApproveResDto kakaoApproveRes) {

        return PayChangeType.DEPOSIT; // 입금
    }

    /***
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel() {

        throw new BusinessLogicException(ExceptionCode.PAYMENT_CANCEL);
    }

    /***
     * 결제 실패
     */
    @GetMapping("/fail")
    public void fail() {

        throw new BusinessLogicException(ExceptionCode.PAYMENT_FAILED);
    }
}
