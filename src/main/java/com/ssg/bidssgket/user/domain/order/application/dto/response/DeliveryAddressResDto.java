package com.ssg.bidssgket.user.domain.order.application.dto.response;

import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddressResDto {

    private Long DeliveryAddressNo; // 배송지 번호
    private String receiverName; // 받는 사람
    private String contactNumber; // 연락처
    private String postcode; // 우편번호
    private String deliveryAddress; // 주소
    private String detailAddress; // 상세 주소
    private String deliveryRequest; // 배송 요청 사항
    private LocalDateTime createdAt; // 등록일
    private LocalDateTime updatedAt; // 수정일
    private boolean isDeleted; // 삭제 여부

    private Long buyerNo; // 구매자 회원 번호
    private Long productNo; // 상품 번호

    public DeliveryAddressResDto(DeliveryAddress deliveryAddress) {
        this.receiverName = deliveryAddress.getReceiverName();
        this.contactNumber = deliveryAddress.getContactNumber();
        this.postcode = deliveryAddress.getPostcode();
        this.deliveryAddress = deliveryAddress.getDeliveryAddressNo().toString();
        this.detailAddress = deliveryAddress.getDetailAddress();
        this.deliveryRequest = deliveryAddress.getDeliveryRequest();
        this.createdAt = deliveryAddress.getCreatedAt();
        this.updatedAt = deliveryAddress.getUpdatedAt();
        this.isDeleted = deliveryAddress.getIsDeleted();

        this.buyerNo = deliveryAddress.getMember().getMemberNo();
        this.productNo = deliveryAddress.getProduct().getProductNo();
    }
}
