package com.ssg.bidssgket.user.domain.payment.view;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.payment.application.dto.request.PaymentReqDto;
import com.ssg.bidssgket.user.domain.payment.application.dto.response.PayResDto;
import com.ssg.bidssgket.user.domain.payment.application.service.PayService;
import com.ssg.bidssgket.user.domain.payment.application.service.PaymentService;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/payment")
@Slf4j
public class PaymentViewController {

    private final ProductService productService;
    private final MemberTestService memberTestService;
    private final PayService payService;
    private final PaymentService paymentService;

    @Autowired
    public PaymentViewController(ProductService productService, MemberTestService memberTestService, PayService payService, PaymentService paymentService) {

        this.productService = productService;
        this.memberTestService = memberTestService;
        this.payService = payService;
        this.paymentService = paymentService;
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
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 2. 세션 정보(email)를 통해 데이터베이스에서 회원 정보 가져오기
        String email = sessionMember.getEmail();
        log.info("(Session Member) Email: {}", email);

        Member member = memberTestService.getMemberByEmail(email);
        log.info("(Member) Member: {}", member);

        // 3. 상품 정보 가져오기
        ProductResDto product = productService.findProductByNo(productNo);
        log.info("[ProductService] (findProductByNo) product: {}", product);

        // 4. 상품 판매 상태 확인
        if (!product.getSalesStatus().equals("selling")) {
            log.warn("상품 상태가 판매중이 아닙니다. (ProductName: {}, Status: {})", product.getProductName(), product.getSalesStatus());

            return "redirect:/"; // 메인 페이지로 리다이렉트
        }

        // 4. 비스킷 페이 정보 가져오기
        Pay pay = payService.getOrCreatePay(member);
        log.info("[PayService] (getOrCreatePay) pay: {}", pay);

        // 5.모델 추가
        model.addAttribute("member", new SessionMember(member));
        model.addAttribute("product", product);
        model.addAttribute("pay", new PayResDto(pay));

        // 결제 페이지로 이동
        return "/user/payment/checkout";
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentReqDto paymentReq) {

        if (paymentReq.getEmail() == null) {
            throw new IllegalArgumentException("회원의 이메일 정보가 없습니다.");
        }

        if (paymentReq.getProductNo() == null) {
            throw new IllegalArgumentException("상품 정보가 없습니다.");
        }

        // 이메일로 회원 정보 조회
        Member member = memberTestService.getMemberByEmail(paymentReq.getEmail());

        try {
            // 결제 처리 로직 실행
            paymentService.processPayment(
                    member.getMemberNo(),
                    paymentReq.getProductNo(),
                    paymentReq.getPaymentAmount(),
                    paymentReq.getPaymentType(),
                    paymentReq.getPaymentTransactionType(),
                    paymentReq.getDeliveryType(),
                    paymentReq.getOrderTransactionType(),

                    paymentReq.getReceiverName(),
                    paymentReq.getContactNumber(),
                    paymentReq.getPostcode(),
                    paymentReq.getDeliveryAddress(),
                    paymentReq.getDetailAddress(),
                    paymentReq.getDeliveryRequest()
            );

            log.info("[processPayment] (Completed) memberNo: {}, productNo: {}",
                    member.getMemberNo(), paymentReq.getProductNo());

            // 응답 데이터 준비
            Map<String, String> response = new HashMap<>();
            response.put("message", "결제가 완료되었습니다.");
            response.put("status", "success");

            // HTTP 201 상태 코드와 함께 응답 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            log.error("결제 처리중 오류가 발생했습니다. >>>> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 처리 중 오류가 발생했습니다.");
        }
    }
}