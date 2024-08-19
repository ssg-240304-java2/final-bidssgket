package com.ssg.bidssgket.user.domain.order.domain.repository;

import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
