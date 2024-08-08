package com.ssg.bidssgket.user.domain.payment.application.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import com.ssg.bidssgket.user.domain.payment.domain.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayService {

    @Autowired
    private PayRepository payRepository;

    public Pay getOrCreatePay(Member member) {
        Optional<Pay> payOptional = payRepository.findByMember(member);

        if (payOptional.isPresent()) {

            return payOptional.get();
        } else {

            // 새로운 Pay 객체를 생성하여 저장
            Pay newPay = Pay.builder()
                    .member(member)
                    .payBalance(0) // 초기 잔액을 0으로 설정 (필요에 따라 변경)
                    .build();

            return payRepository.save(newPay);
        }
    }

    public void updatePay(Pay pay) {
        payRepository.save(pay);
    }
}
