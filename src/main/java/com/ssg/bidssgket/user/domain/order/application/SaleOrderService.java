package com.ssg.bidssgket.user.domain.order.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.Parcel;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderStatus;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.SaleOrderRepository;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;

    public SaleOrderService(SaleOrderRepository saleOrderRepository) {
        this.saleOrderRepository = saleOrderRepository;
    }

    @Transactional
    public SaleOrder createAndSaveSaleOrder(OrderTransactionType orderTransactionType, DeliveryType deliveryType,
                                            OrderStatus orderStatus, Member member, Product product, Payment payment, Parcel parcel, DeliveryAddress deliveryAddress) {

        // SaleOrder 객체 생성
        SaleOrder saleOrder = SaleOrder.addSaleOrder(
                orderTransactionType,
                deliveryType,
                orderStatus,
                member,
                product,
                payment,
                parcel,
                deliveryAddress
        );

        // SaleOrder 객체 저장
        return saleOrderRepository.save(saleOrder);
    }
}
