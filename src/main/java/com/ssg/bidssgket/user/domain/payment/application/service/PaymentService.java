package com.ssg.bidssgket.user.domain.payment.application.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.application.OrderService;
import com.ssg.bidssgket.user.domain.order.domain.DeliveryAddress;
import com.ssg.bidssgket.user.domain.order.domain.enums.DeliveryType;
import com.ssg.bidssgket.user.domain.order.domain.enums.OrderTransactionType;
import com.ssg.bidssgket.user.domain.order.domain.repository.DeliveryAddressRepository;
import com.ssg.bidssgket.user.domain.payment.api.service.MemberTestService;
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
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PaymentService {

    private final PayChangeRepository payChangeRepository;
    private final PayRepository payRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final MemberTestService memberTestService;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    public PaymentService(PayChangeRepository payChangeRepository, PayRepository payRepository, PaymentRepository paymentRepository, OrderService orderService, MemberTestService memberTestService, ProductRepository productRepository, DeliveryAddressRepository deliveryAddressRepository) {
        this.payChangeRepository = payChangeRepository;
        this.payRepository = payRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.memberTestService = memberTestService;
        this.productRepository = productRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Transactional
    public void processPayment(Long memberNo, Long productNo, int paymentAmount, PaymentType paymentType, PaymentTransactionType paymentTransactionType, DeliveryType deliveryType, OrderTransactionType orderTransactionType,
                               String receiverName, String contactNumber, String postcode, String deliveryAddress, String detailAddress, String deliveryRequest) {

        // 1. 회원 정보 조회
        Member member = memberTestService.getMember(memberNo);

        // 2. 상품 정보 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품의 정보가 존재하지 않습니다."));

        // 3. 안전거래(ESCROW)일 경우에만 결제 및 배송 정보 처리
        Payment payment = null;

        if (deliveryType == DeliveryType.ESCROW) {

            // 4. 결제 처리
            payment = handleEscrowPayment(memberNo, paymentAmount, paymentType, paymentTransactionType);

            // 5. 배송 정보 저장
            DeliveryAddress deliveryAddressEntity = DeliveryAddress.addDeliveryAddress(member, product, receiverName, contactNumber, postcode, deliveryAddress, detailAddress, deliveryRequest);
            deliveryAddressRepository.save(deliveryAddressEntity);

        }

        // 주문서 생성
        orderService.createOrders(member, product, payment, orderTransactionType, deliveryType);
    }

    private Payment handleEscrowPayment(Long memberNo, int paymentAmount, PaymentType paymentType, PaymentTransactionType paymentTransactionType) {

        // 1. 회원 정보 조회
        Member member = memberTestService.getMember(memberNo);
        log.info("[PaymentService] getMember: {}", memberNo);

        // 2. 회원의 현재 잔액 조회
        Pay pay = payRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 비스킷 페이 정보가 존재하지 않습니다."));

        int currentBalance = pay.getPayBalance();
        int updatedBalance = currentBalance - paymentAmount;

        // 잔액 부족 확인
        if (updatedBalance < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 3. 잔액 변경 내역 기록
        PayChange payChange = createPayChange(pay, paymentAmount, currentBalance, updatedBalance);

        // 4. Pay Table 업데이트
        pay.setPayBalance(updatedBalance);
        payRepository.save(pay);

        // 5. Payment 테이블 결제 정보 생성 및 저장
        Payment payment = Payment.addpayment(
                paymentType,
                paymentTransactionType,
                paymentAmount,
                PaymentStatus.PAID,
                member,
                payChange
        );
        paymentRepository.save(payment);

        return payment;
    }

    private PayChange createPayChange(Pay pay, int paymentAmount, int currentBalance, int updatedBalance) {

        PayChange payChange = PayChange.builder()
                .payChangeType(PayChangeType.WITHDRAWAL)
                .payChangeAmount(paymentAmount)
                .balance(currentBalance)
                .updatedBalance(updatedBalance)
                .pay(pay)
                .build();

        payChangeRepository.save(payChange);

        return payChange;
    }
}