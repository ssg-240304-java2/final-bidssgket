package com.ssg.bidssgket.user.domain.payment.api.controller;

import com.ssg.bidssgket.user.domain.payment.api.service.PaymentApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentApiController {
    
    private PaymentApiService paymentApiService;

    @Autowired
    public PaymentApiController(PaymentApiService paymentApiService) {
        this.paymentApiService = paymentApiService;
    }
}