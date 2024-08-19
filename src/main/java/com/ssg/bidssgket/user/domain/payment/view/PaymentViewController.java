package com.ssg.bidssgket.user.domain.payment.view;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.payment.application.service.PayService;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/payment")
@Slf4j
public class PaymentViewController {

    private final ProductService productService;
    private final MemberTestService memberTestService;
    private final PayService payService;

    @Autowired
    public PaymentViewController(ProductService productService, MemberTestService memberTestService, PayService payService) {

        this.productService = productService;
        this.memberTestService = memberTestService;
        this.payService = payService;
    }

    @GetMapping("/info")
    public String paymentInfo() { return "/user/payment/info"; }

    @GetMapping("/deposit")
    public String paymentDeposit() { return "/user/payment/deposit"; }

    @GetMapping("/withdrawal")
    public String paymentWithdrawal() { return "/user/payment/withdrawal"; }

    @GetMapping("/checkout/{productNo}")
    public String showPaymentPage(@PathVariable("productNo") Long productNo,
                                  HttpSession session,
                                  Model model) {

        // 1. 세션에서 현재 로그인한 회원 정보 가져오기
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        if (sessionMember == null) {
            // 세션에 회원 정보가 없는 경우, 로그인 페이지로 리다이렉트하거나 오류 처리
            return "redirect:/user/member/login"; // 로그인 페이지로 리다이렉트
        }

        // 2. 이메일을 통해 데이터베이스에서 회원 정보 가져오기 (이미 세션에 있으므로 생략 가능)
        String email = sessionMember.getEmail();
        Member member = memberTestService.getMemberByEmail(email);
        log.info("[MemberTestService] (getMemberByEmail) member: {}", member.getEmail());

        // 2. 상품 정보 가져오기
        ProductResDto product = productService.findProductByNo(productNo);
        log.info("[ProductService] (findProductByNo) product: {}", product);

        // 3. 비스킷 페이 정보 가져오기
        Pay pay = payService.getOrCreatePay(member);
        log.info("[PayService] (getOrCreatePay) pay: {}", pay);


        // 3. 모델에 회원 정보와 상품 정보 추가
        model.addAttribute("member", member);
        model.addAttribute("product", product);
        model.addAttribute("pay", pay);

        // 결제 페이지로 이동
        return "/user/payment/checkout";
    }
}
