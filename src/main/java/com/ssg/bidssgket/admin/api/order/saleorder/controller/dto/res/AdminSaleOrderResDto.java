package com.ssg.bidssgket.admin.api.order.saleorder.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminSaleOrderResDto {

    private Long saleOrderNo; // 구매 주문 번호 [PK]
    private String orderTransactionType; // 경매, 즉시구매
    private String deliveryType; // 직거래, 안전거래
    private String orderStatus; // 결제대기, 결제완료, 배송대기, 배송중, 배송완료, 주문완료, 결제취소, 주문취소
    private String paymentStatus; // 결제완료, 결제취소
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;
    private Long productNo;
    private Long memberNo;

    public AdminSaleOrderResDto(SaleOrder saleOrder) {
        this.saleOrderNo = saleOrder.getSaleOrderNo();
        this.orderTransactionType = saleOrder.getOrderTransactionType().equals(OrderTransactionType.AUCTION) ? "경매" : "즉시구매";
        this.deliveryType = saleOrder.getDeliveryType().equals(DeliveryType.IN_PERSON) ? "직거래" : "안전거래";
        this.orderStatus = saleOrder.getOrderStatus().getVal();
        this.paymentStatus = saleOrder.getPayment().getPaymentStatus().equals(PaymentStatus.PAID) ? "결제완료" : "결제취소";
        this.createdAt = saleOrder.getCreatedAt();
        this.updatedAt = saleOrder.getUpdatedAt();
        this.productNo = saleOrder.getProduct().getProductNo();
        this.memberNo = saleOrder.getMember().getMemberNo();
    }
}
