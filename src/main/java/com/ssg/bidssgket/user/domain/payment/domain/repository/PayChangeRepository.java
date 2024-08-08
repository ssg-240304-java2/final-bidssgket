package com.ssg.bidssgket.user.domain.payment.domain.repository;

import com.ssg.bidssgket.user.domain.payment.domain.PayChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayChangeRepository extends JpaRepository<PayChange, Long> {
}
