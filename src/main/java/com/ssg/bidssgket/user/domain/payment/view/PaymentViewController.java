package com.ssg.bidssgket.user.domain.payment.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentViewController {

    @GetMapping("/info")
    public String paymentInfo() { return "/user/payment/info"; }

    @GetMapping("/deposit")
    public String paymentDeposit() { return "/user/payment/deposit"; }

    @GetMapping("/withdrawal")
    public String paymentWithdrawal() { return "/user/payment/withdrawal"; }
}
