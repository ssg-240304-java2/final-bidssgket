package com.ssg.bidssgket.user.domain.payment.domain;

import com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="pay_change")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class PayChange {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long payChangeNo; // 비스킷 페이 잔액 변경 내역 번호 [PK]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayChangeType payChangeType; // 입금, 출금

    @Column(nullable = false)
    private int payChangeAmount; // 변경 금액 (양수: 입금 / 음수: 출금)

    @Column(nullable = false)
    private int balance; // 보유 잔액

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt; // 등록일

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payNo", nullable=false)
    private Pay pay; // 페이 [FK]

    @Builder
    public PayChange(PayChangeType payChangeType, int payChangeAmount, int balance, Pay pay) {
        this.payChangeType = payChangeType;
        this.payChangeAmount = payChangeAmount;
        this.balance = balance;
        this.pay = pay;
        this.createdAt = LocalDateTime.now();
    }

    public static PayChange addPayChange(PayChangeType payChangeType, int payChangeAmount, int initialBalance, Pay pay) {
        int updatedBalance = calculateBalance(payChangeType, payChangeAmount, initialBalance);

        return PayChange.builder()
                .payChangeType(payChangeType)
                .payChangeAmount(payChangeAmount)
                .balance(updatedBalance)
                .pay(pay)
                .build();
    }

    /***
     * 잔액 계산 메서드
     * @param payChangeType 비스킷 페이 잔액 변동 유형 (입금/출금)
     * @param payChangeAmount 비스킷 페이 잔액 변동 금액
     * @param initialBalance 비스킷 페이 초기 잔액
     * @return 업데이트 후 잔액 or 초기 잔액
     */
    private static int calculateBalance(PayChangeType payChangeType, int payChangeAmount, int initialBalance) {
        if (payChangeType == PayChangeType.DEPOSIT) {
            return initialBalance + payChangeAmount;
        } else if (payChangeType == PayChangeType.WITHDRAWAL) {
            return initialBalance - payChangeAmount;
        }

        return initialBalance;
    }

    /***
     * 페이 정보 설정 메서드
     * @param pay 페이 정보
     */
    public void setPay(Pay pay) {
        this.pay = pay;
        if (!pay.getPayChangeList().contains(this)) {
            pay.getPayChangeList().add(this);
        }
    }
}