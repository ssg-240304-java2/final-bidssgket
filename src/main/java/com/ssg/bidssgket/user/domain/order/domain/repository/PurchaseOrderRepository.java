package com.ssg.bidssgket.user.domain.order.domain.repository;

import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("SELECT p.product From PurchaseOrder AS p WHERE p.member.memberNo = :memberNo AND p.orderStatus NOT IN ('PENDING_PAYMENT', 'PAYMENT_CANCELLED', 'ORDER_CANCELLED', 'PAID') ORDER BY p.createdAt DESC ")
    List<Product> getPurchaseTradingProducts(Long memberNo);

    @Query("SELECT p.product From PurchaseOrder AS p WHERE p.member.memberNo = :memberNo AND p.orderStatus = 'PAID' ORDER BY p.createdAt DESC ")
    List<Product> getPurchaseCompletedProducts(Long memberNo);

    @Query("SELECT p.product FROM PurchaseOrder AS p WHERE p.member.memberNo = :memberNo AND p.orderStatus NOT IN ('PENDING_PAYMENT', 'PAYMENT_CANCELLED', 'ORDER_CANCELLED') ORDER BY p.createdAt DESC ")
    List<PurchaseOrder> getPurchaseTradingOrdersByMember(Long memberNo);
}
