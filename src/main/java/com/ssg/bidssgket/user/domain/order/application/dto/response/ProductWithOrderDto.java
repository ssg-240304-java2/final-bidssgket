package com.ssg.bidssgket.user.domain.order.application.dto.response;

import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductWithOrderDto {
    private Product product;
    private PurchaseOrder purchaseOrder;

    // 생성자
    public ProductWithOrderDto(Product product, PurchaseOrder purchaseOrder) {
        this.product = product;
        this.purchaseOrder = purchaseOrder;
    }

    // Setter
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}