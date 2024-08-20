package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.PurchaseOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional
    public PurchaseOrder createAndSavePurchaseOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType,
                                                    OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel) {

        // PurchaseOrder 객체 생성
        PurchaseOrder purchaseOrder = PurchaseOrder.addPurchaseOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                payment,
                parcel
        );

        // PurchaseOrder 객체 저장
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
