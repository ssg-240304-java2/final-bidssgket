package com.ssg.bidssgket.user.domain.order.application.dto.response;

import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParcelResDto {
    private Long parcelNo;
    private String courierName;
    private String trackingNum;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    private Long memberNo;
    private Long purchaseOrderNo;
    private Long saleOrderNo;

    public ParcelResDto(Parcel parcel) {
        this.parcelNo = parcel.getParcelNo();
        this.courierName = parcel.getCourierName();
        this.trackingNum = parcel.getTrackingNum();
        this.deliveryStatus = parcel.getDeliveryStatus();
        this.createdAt = parcel.getCreatedAt();
        this.updatedAt = parcel.getUpdatedAt();
        this.isDeleted = parcel.getIsDeleted();

        this.memberNo = parcel.getMember().getMemberNo();
        this.purchaseOrderNo = parcel.getPurchaseOrder().getPurchaseOrderNo();
        this.saleOrderNo = parcel.getSaleOrder().getSaleOrderNo();
    }
}