package com.ssg.bidssgket.user.domain.order.domain.repository;

import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}
