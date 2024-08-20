package com.ssg.bidssgket.user.domain.payment.application.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.OrderService;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.PayChange;
import com.ssg.bidssgket.user.domain.payment.domain.Payment;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PayChangeType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentStatus;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentTransactionType;
import com.ssg.bidssgket.user.domain.payment.domain.enums.PaymentType;
import com.ssg.bidssgket.user.domain.payment.domain.repository.PayChangeRepository;
import com.ssg.bidssgket.user.domain.payment.domain.repository.PayRepository;
import com.ssg.bidssgket.user.domain.payment.domain.repository.PaymentRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentService {

    private final PayChangeRepository payChangeRepository;
    private final PayRepository payRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PayChangeRepository payChangeRepository, PayRepository payRepository, PaymentRepository paymentRepository, OrderService orderService) {
        this.payChangeRepository = payChangeRepository;
        this.payRepository = payRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void processPayment(Member member, Product product, int paymentAmount, PaymentTransactionType paymentTransactionType, DeliveryType deliveryType, OrderTransactionType orderTransactionType) {

        Payment payment = null;

        if (deliveryType == DeliveryType.ESCROW) {
            // 1. Pay Table에서 현재 잔액 조회
            Optional<Pay> optionalPay = payRepository.findByMember(member);
            Pay pay = optionalPay.orElseThrow(() -> new IllegalArgumentException("해당 회원의 결제 정보가 존재하지 않습니다."));

            int currentBalance = pay.getPayBalance();
            int updatedBalance = currentBalance - paymentAmount;

            // 잔액 부족 확인
            if (updatedBalance < 0) {
                throw new IllegalArgumentException("잔액이 부족합니다.");
            }

            // 2. Pay_change Table Update
            PayChange payChange = PayChange.builder()
                    .payChangeType(PayChangeType.WITHDRAWAL)
                    .payChangeAmount(paymentAmount)
                    .balance(currentBalance)
                    .updatedBalance(updatedBalance)
                    .pay(pay)
                    .build();

            payChangeRepository.save(payChange);

            // 3. Pay Table Update
            pay.setPayBalance(updatedBalance);
            payRepository.save(pay);

            // 4. Payment Table 결제 정보 생성 및 저장
            payment = Payment.addpayment(
                    PaymentType.BISCUIT_PAY,
                    paymentTransactionType,
                    paymentAmount,
                    PaymentStatus.PAID,
                    member,
                    payChange
            );

            paymentRepository.save(payment);
        }

        // 5. OrderService를 통해 PurchaseOrder 및 SaleOrder 생성
        orderService.createOrders(member, product, payment, orderTransactionType, deliveryType);
    }
}
