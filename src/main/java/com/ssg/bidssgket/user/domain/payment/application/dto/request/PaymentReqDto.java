package com.ssg.bidssgket.user.domain.payment.application.dto.request;

import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentTransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentReqDto {

    @NotNull
    private String email; // 회원 이메일

    @NotNull
    private Long productNo; // 상품 번호

    @NotNull
    private PaymentType paymentType; // 카카오페이, 비스킷페이

    @NotNull
    private PaymentTransactionType paymentTransactionType; // DEPOSIT, WITHDRAWAL

    @NotNull
    private int paymentAmount; // 결제 금액

    @NotNull
    private DeliveryType deliveryType; // 배송 유형

    @NotNull
    private OrderTransactionType orderTransactionType; // 주문 거래 유형

    @NotNull
    private String receiverName; // 받는 사람

    @NotNull
    private String contactNumber; // 연락처

    @NotNull
    private String postcode; // 우편번호

    @NotNull
    private String deliveryAddress; // 주소

    @NotNull
    private String detailAddress; // 상세 주소

    @NotNull
    private String deliveryRequest; // 배송 요청 사항

    public PaymentReqDto(String email, Long productNo, PaymentType paymentType, PaymentTransactionType paymentTransactionType, int paymentAmount, DeliveryType deliveryType, OrderTransactionType orderTransactionType,
                         String receiverName, String contactNumber, String postcode, String deliveryAddress, String detailAddress, String deliveryRequest) {
        this.email = email;
        this.productNo = productNo;
        this.paymentType = paymentType;
        this.paymentTransactionType = paymentTransactionType;
        this.paymentAmount = paymentAmount;
        this.deliveryType = deliveryType;
        this.orderTransactionType = orderTransactionType;

        this.receiverName = receiverName;
        this.contactNumber = contactNumber;
        this.postcode = postcode;
        this.deliveryAddress = deliveryAddress;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;
    }
}