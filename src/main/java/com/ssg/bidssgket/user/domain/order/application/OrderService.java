package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.PurchaseOrderRepository;
import com.ssg.bidssgket.user.domain.order.domain.repository.SaleOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleOrderRepository saleOrderRepository;

    public OrderService(PurchaseOrderRepository purchaseOrderRepository, SaleOrderRepository saleOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.saleOrderRepository = saleOrderRepository;
    }

    @Transactional
    public void createOrders(Member member, Product product, Payment payment, OrderTransactionType orderTransactionType, DeliveryType deliveryType) {
        PurchaseOrder purchaseOrder;
        SaleOrder saleOrder;

        // 구매 주문서 생성
        if (deliveryType == DeliveryType.IN_PERSON) {

            // 직거래인 경우 결제 없이 주문서 생성
            purchaseOrder = PurchaseOrder.addPurchaseOrder(
                    orderTransactionType,
                    DeliveryType.IN_PERSON,
                    OrderStatus.COMPLETED,
                    member,
                    product,
                    null, // 직거래인 경우 결제 정보 없음
                    null // 직거래는 Parcel 정보 없음
            );
        } else if (deliveryType == DeliveryType.ESCROW) {

            // 안전거래인 경우 결제 정보 포함하여 주문서 생성
            purchaseOrder = PurchaseOrder.addPurchaseOrder(
                    orderTransactionType,
                    DeliveryType.ESCROW,
                    OrderStatus.PAID,
                    member,
                    product,
                    payment,
                    null // 초기에는 Parcel 정보가 없을 수 있음
            );
        } else {
            throw new IllegalArgumentException("지원되지 않는 배송 유형입니다.");
        }

        // 구매 주문서 저장
        purchaseOrderRepository.save(purchaseOrder);

        // 판매 주문서 생성
        if (deliveryType == DeliveryType.IN_PERSON) {
            // 직거래인 경우 결제 없이 판매 주문서 생성
            saleOrder = SaleOrder.addSaleOrder(
                    orderTransactionType,
                    DeliveryType.IN_PERSON,
                    OrderStatus.COMPLETED,
                    member,
                    product,
                    null, // 직거래인 경우 결제 정보 없음
                    null // 초기에는 Parcel 정보가 없을 수 있음
            );
        } else if (deliveryType == DeliveryType.ESCROW) {

            // 안전거래인 경우 결제 정보 포함하여 판매 주문서 생성
            saleOrder = SaleOrder.addSaleOrder(
                    orderTransactionType,
                    DeliveryType.ESCROW,
                    OrderStatus.PAID,
                    member,
                    product,
                    payment,
                    null // 초기에는 Parcel 정보가 없을 수 있음
            );
        } else {
            throw new IllegalArgumentException("지원되지 않는 배송 유형입니다.");
        }

        // 판매 주문서 저장
        saleOrderRepository.save(saleOrder);
    }
}