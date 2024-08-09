package com.ssg.bidssgket.admin.api.payment.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.bidssgket.user.domain.order.domain.enums.TransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminPaymentResDto {
    private Long paymentNo; // 결제 번호 [PK]
    private String paymentType; // 카카오페이, 비스킷페이
    private String transactionType; // 입금, 출금
    private int amount; // 결제 금액
    private String paymentStatus; // 결제완료, 결제취소
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Long memberNo;


    public AdminPaymentResDto(Payment payment) {
        this.paymentNo = payment.getPaymentNo();
        this.paymentType = payment.getPaymentType().equals(PaymentType.BISCUIT_PAY) ? "비스킷페이" : "카카오페이";
        this.transactionType = payment.getTransactionType().equals(TransactionType.AUCTION) ? "경매" : "즉시구매";
        this.amount = payment.getAmount();
        this.paymentStatus = payment.getPaymentStatus().equals(PaymentStatus.PAID) ? "결제완로" : "결제취소";
        this.createdAt = payment.getCreatedAt();
        this.memberNo = payment.getMember().getMemberNo();
    }
}
