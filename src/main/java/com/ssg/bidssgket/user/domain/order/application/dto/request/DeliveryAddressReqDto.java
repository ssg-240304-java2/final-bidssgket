package com.ssg.bidssgket.user.domain.order.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddressReqDto {

    private String receiverName; // 받는 사람
    private String contactNumber; // 연락처
    private String postcode; // 우편번호
    private String deliveryAddress; // 주소
    private String detailAddress; // 상세 주소
    private String deliveryRequest; // 배송 요청 사항

    private Long buyerNo; // 구매자 회원 번호
    private Long productNo; // 상품 번호

    public DeliveryAddressReqDto(String receiverName, String contactNumber, String postcode, String deliveryAddress, String detailAddress, String deliveryRequest, Long buyerNo, Long productNo) {

        this.receiverName = receiverName;
        this.contactNumber = contactNumber;
        this.postcode = postcode;
        this.deliveryAddress = deliveryAddress;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;

        this.buyerNo = buyerNo;
        this.productNo = productNo;
    }
}
