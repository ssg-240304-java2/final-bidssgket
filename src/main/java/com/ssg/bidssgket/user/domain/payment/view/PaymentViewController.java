package com.ssg.bidssgket.user.domain.payment.view;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/checkout/{productNo}")
    public String showPaymentPage(@PathVariable("productNo") Long productNo,
                                  Authentication authentication,
                                  Model model) {

//        // 1. 현재 로그인한 회원 정보 가져오기
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String email = oAuth2User.getAttribute("email"); // 이메일 정보 추출
//        log.info("[PaymentViewController] (showPaymentPage) User email: {}", email);

//         Test용 이메일
        String email = "finite2030@gmail.com";

//         이메일을 통해 데이터베이스에서 회원 정보 가져오기
        Member member = memberTestService.getMemberByEmail(email);
        log.info("[MemberTestService] (getMemberByMemberName) member: {}", member.getEmail());

        // 2. 상품 정보 가져오기
        ProductResDto product = productService.findProductByNo(productNo);
        log.info("[ProductService] (findProductByNo) product: {}", product);


        // 3. 모델에 회원 정보와 상품 정보 추가
        model.addAttribute("member", member);
        model.addAttribute("product", product);

        // 결제 페이지로 이동
        return "/user/payment/checkout";
    }
}
