package com.ssg.bidssgket.user.domain.payment.domain.repository;

import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
