package com.ssg.bidssgket.user.domain.order.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderViewController {

    @GetMapping("/mypage")
    public String mypage() { return "/user/order/mypage"; }

    @GetMapping("/order/checkout")
    public String productCheckout() { return "/user/order/checkout"; }
}
