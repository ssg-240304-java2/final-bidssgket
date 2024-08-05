package com.ssg.bidssgket.user.domain.order.application.dto.request;

import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParcelReqDto {
    private String courierName;
    private String trackingNum;
    private DeliveryStatus deliveryStatus;

    private Long sellerNo;
    private Long purchaseOrderNo;
    private Long saleOrderNo;

    public ParcelReqDto(String courierName, String trackingNum, DeliveryStatus deliveryStatus, Long sellerNo, Long purchaseOrderNo, Long saleOrderNo) {
        this.courierName = courierName;
        this.trackingNum = trackingNum;
        this.deliveryStatus = deliveryStatus;

        this.sellerNo = sellerNo;
        this.purchaseOrderNo = purchaseOrderNo;
        this.saleOrderNo = saleOrderNo;
    }
}
