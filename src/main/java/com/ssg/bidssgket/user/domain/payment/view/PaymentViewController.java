package com.ssg.bidssgket.user.domain.payment.view;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentViewController {

    private final ProductService productService;
    private final MemberTestService memberTestService;

    @Autowired
    public PaymentViewController(ProductService productService, MemberTestService memberTestService) {

        this.productService = productService;
        this.memberTestService = memberTestService;
    }

    @GetMapping("/info")
    public String paymentInfo() { return "/user/payment/info"; }

    @GetMapping("/deposit")
    public String paymentDeposit() { return "/user/payment/deposit"; }

    @GetMapping("/withdrawal")
    public String paymentWithdrawal() { return "/user/payment/withdrawal"; }

    @GetMapping("/checkout")
    public String showPaymentPage(@RequestParam("productNo") Long productNo,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        // 1. 현재 로그인한 회원 정보 가져오기
        Member member = memberTestService.getMemberByMemberName(userDetails.getUsername());

        // 2. 상품 정보 가져오기
        Product product = productService.findProductByNo(productNo);

        // 3. 모델에 회원 정보와 상품 정보 추가
        model.addAttribute("member", member);
        model.addAttribute("product", product);

        // 결제 페이지로 이동
        return "/user/payment/checkout";
    }
}
