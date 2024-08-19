package com.ssg.bidssgket.user.domain.payment.api.repository;

import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentApiRepository extends JpaRepository<Payment, Long> {

}