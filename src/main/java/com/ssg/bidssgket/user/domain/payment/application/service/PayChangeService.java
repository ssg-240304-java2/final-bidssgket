package com.ssg.bidssgket.user.domain.payment.application.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.PayChange;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType;
import com.ssg.bidssgket.user.domain.payment.domain.repository.PayChangeRepository;
import com.ssg.bidssgket.user.domain.payment.exception.BusinessLogicException;
import com.ssg.bidssgket.user.domain.payment.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayChangeService {

    @Autowired
    private PayChangeRepository payChangeRepository;

    public PayChange savePayChange(PayChangeType payChangeType, int payChangeAmount, int initialBalance, Pay pay) {
        // PayChange 엔티티 생성
        int updatedBalance = calculateBalance(payChangeType, payChangeAmount, initialBalance);

        PayChange payChange = PayChange.builder()
                .payChangeType(payChangeType)
                .payChangeAmount(payChangeAmount)
                .balance(initialBalance)
                .updatedBalance(updatedBalance)
                .pay(pay)
                .build();

        // PayChange 엔티티 저장
        return payChangeRepository.save(payChange);
    }

    /***
     * 잔액 계산 메서드
     * @param payChangeType 비스킷 페이 잔액 변동 유형 (입금/출금)
     * @param payChangeAmount 비스킷 페이 잔액 변동 금액
     * @param initialBalance 비스킷 페이 초기 잔액
     * @return 업데이트 후 잔액 or 초기 잔액
     */
    private int calculateBalance(PayChangeType payChangeType, int payChangeAmount, int initialBalance) {
        if (payChangeType == PayChangeType.DEPOSIT) {

            return initialBalance + payChangeAmount;
        } else if (payChangeType == PayChangeType.WITHDRAWAL) {
            if (initialBalance - payChangeAmount < 0) {

                throw new BusinessLogicException(ExceptionCode.INSUFFICIENT_BALANCE, () -> "잔액이 부족합니다.");
            }

            return initialBalance - payChangeAmount;
        }

        return initialBalance;
    }
}