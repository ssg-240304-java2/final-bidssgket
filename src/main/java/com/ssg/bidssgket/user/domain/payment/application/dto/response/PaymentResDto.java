package com.ssg.bidssgket.user.domain.payment.application.dto.response;

import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentResDto {
    private Long paymentNo;
    private Long memberNo;
    private String paymentType;
    private String transactionType;
    private int amount;
    private String paymentStatus;

    private Long payChangeNo;
    private Long purchaseOrderNo;
    private Long saleOrderNo;

    public PaymentResDto(Payment payment) {
        this.paymentNo = payment.getPaymentNo();
        this.memberNo = payment.getMember().getMemberNo();
        this.paymentType = payment.getPaymentType().name();
        this.transactionType = payment.getTransactionType().name();
        this.amount = payment.getAmount();
        this.paymentStatus = payment.getPaymentStatus().name();

        this.payChangeNo = payment.getPayChange().getPayChangeNo();
        this.purchaseOrderNo = payment.getPurchaseOrder().getPurchaseOrderNo();
        this.saleOrderNo = payment.getSaleOrder().getSaleOrderNo();
    }
}