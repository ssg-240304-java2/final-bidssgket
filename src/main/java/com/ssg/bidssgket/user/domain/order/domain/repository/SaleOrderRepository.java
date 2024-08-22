package com.ssg.bidssgket.user.domain.order.domain.repository;

import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
}
