package com.ssg.bidssgket.user.domain.payment.view;

import com.ssg.bidssgket.user.domain.auction.application.AuctionService;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.SessionMember;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
import com.ssg.bidssgket.user.domain.payment.application.dto.request.PaymentReqDto;
import com.ssg.bidssgket.user.domain.payment.application.dto.response.PayResDto;
import com.ssg.bidssgket.user.domain.payment.application.service.PayService;
import com.ssg.bidssgket.user.domain.payment.application.service.PaymentService;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.domain.Product;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/payment")
@Slf4j
public class PaymentViewController {

    private final ProductService productService;
    private final MemberTestService memberTestService;
    private final PayService payService;
    private final PaymentService paymentService;
    private final AuctionService auctionService;

    @Autowired
    public PaymentViewController(ProductService productService, MemberTestService memberTestService, PayService payService, PaymentService paymentService, AuctionService auctionService) {
        this.productService = productService;
        this.memberTestService = memberTestService;
        this.payService = payService;
        this.paymentService = paymentService;
        this.auctionService = auctionService;
    }

    @GetMapping("/info")
    public String paymentInfo(Model model, HttpSession session) {
        Member member = getSessionMember(session);
        Long memberNo = member.getMemberNo();
        if (member == null) {
            return "redirect:/login";
        }

        Pay pay = payService.getOrCreatePay(member);
        log.info("(Member) Member pay = {}", pay);

        List<Payment> payments = paymentService.findbyMemberNo(member);

        model.addAttribute("pay", new PayResDto(pay));
        model.addAttribute("payments", payments);
        return "/user/payment/info";
    }

    @GetMapping("/deposit")
    public String paymentDeposit(Model model, HttpSession session) {
        Member member = getSessionMember(session);
        if (member == null) {
            return "redirect:/login";
        }

        Pay pay = payService.getOrCreatePay(member);
        log.info("(Member) Member pay = {}", pay);

        model.addAttribute("pay", new PayResDto(pay));
        return "/user/payment/deposit";
    }

    @GetMapping("/withdrawal")
    public String paymentWithdrawal() {
        return "/user/payment/withdrawal";
    }

    @GetMapping("/checkout/{productNo}")
    public String showPaymentPage(@PathVariable("productNo") Long productNo,
                                  @RequestParam(value = "deliveryType", required = false) String deliveryType,
                                  HttpSession session, Model model) {
        Member member = getSessionMember(session);
        if (member == null) {
            return "redirect:/login";
        }

        ProductResDto product = productService.findProductByNo(productNo);
        log.info("[ProductService] (findProductByNo) product: {}", product);

        if (!product.getSalesStatus().equals("selling") && !product.getSalesStatus().equals("trading")) {
            log.warn("상품 상태가 판매중이 아닙니다. (ProductName: {}, Status: {})", product.getProductName(), product.getSalesStatus());
            return "redirect:/";
        }


        Pay pay = payService.getOrCreatePay(member);
        log.info("[PayService] (getOrCreatePay) pay: {}", pay);

        boolean isAuctionPay = auctionService.isWinningBidder(member.getMemberNo(), productNo);

        model.addAttribute("member", new SessionMember(member));
        model.addAttribute("product", product);
        model.addAttribute("pay", new PayResDto(pay));
        model.addAttribute("deliveryType", deliveryType);
        model.addAttribute("isAuctionPay", isAuctionPay);

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

        Member member = memberTestService.getMemberByEmail(paymentReq.getEmail());

        try {
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

            Map<String, String> response = new HashMap<>();
            response.put("message", "결제가 완료되었습니다.");
            response.put("status", "success");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("결제 처리중 오류가 발생했습니다. >>>> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 처리 중 오류가 발생했습니다.");
        }
    }

    private Member getSessionMember(HttpSession session) {
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        if (sessionMember != null) {
            return memberTestService.getMemberByEmail(sessionMember.getEmail());
        }
        return null;
    }
}