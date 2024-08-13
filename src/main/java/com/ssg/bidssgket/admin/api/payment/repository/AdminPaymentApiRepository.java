package com.ssg.bidssgket.admin.api.payment.repository;

import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminPaymentApiRepository extends JpaRepository<Payment, Long> {
}
