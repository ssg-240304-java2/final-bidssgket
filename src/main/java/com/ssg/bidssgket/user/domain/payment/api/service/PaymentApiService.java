package com.ssg.bidssgket.user.domain.payment.api.service;

import com.ssg.bidssgket.user.domain.payment.api.repository.PaymentApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentApiService {

    private PaymentApiRepository paymentApiRepository;

    @Autowired
    public PaymentApiService(PaymentApiRepository paymentApiRepository) {
        this.paymentApiRepository = paymentApiRepository;
    }
}