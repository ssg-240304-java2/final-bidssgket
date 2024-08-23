package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.auction.domain.repository.AuctionRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.PurchaseOrderRepository;
import com.ssg.bidssgket.user.domain.order.domain.repository.SaleOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleOrderRepository saleOrderRepository;

    public OrderService(PurchaseOrderRepository purchaseOrderRepository, SaleOrderRepository saleOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.saleOrderRepository = saleOrderRepository;
    }

    @Transactional
    public void createOrders(Member member, Product product, Payment payment, OrderTransactionType orderTransactionType, DeliveryType deliveryType, DeliveryAddress deliveryAddress) {

        OrderStatus orderStatus = determineOrderStatus(deliveryType);

        // 구매 주문서 생성
        PurchaseOrder purchaseOrder = PurchaseOrder.addPurchaseOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                deliveryType == DeliveryType.ESCROW ? payment : null,
                null, // 초기에는 Parcel 정보가 없을 수 있음
                deliveryType == DeliveryType.ESCROW ? deliveryAddress : null
        );

        // 구매 주문서 저장
        purchaseOrderRepository.save(purchaseOrder);

        // 판매 주문서 생성
        SaleOrder saleOrder = SaleOrder.addSaleOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                deliveryType == DeliveryType.ESCROW ? payment : null,
                null, // 초기에는 Parcel 정보가 없을 수 있음
                deliveryType == DeliveryType.ESCROW ? deliveryAddress : null
        );

        // 판매 주문서 저장
        saleOrderRepository.save(saleOrder);
    }

    private OrderStatus determineOrderStatus(DeliveryType deliveryType) {

        if (deliveryType == DeliveryType.IN_PERSON) {

            return OrderStatus.COMPLETED;
        } else if (deliveryType == DeliveryType.ESCROW) {

            return OrderStatus.PAID;
        } else {

            throw new IllegalArgumentException("지원되지 않는 배송 유형입니다.");
        }
    }
}