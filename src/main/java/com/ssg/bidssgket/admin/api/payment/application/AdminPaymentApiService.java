package com.ssg.bidssgket.admin.api.payment.application;

import com.ssg.bidssgket.admin.api.payment.controller.dto.res.AdminPaymentResDto;
import com.ssg.bidssgket.admin.api.payment.repository.AdminPaymentApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPaymentApiService {

    private final AdminPaymentApiRepository adminPaymentApiRepository;

    public Page<AdminPaymentResDto> getPaymentList(Pageable pageable) {
        Page<AdminPaymentResDto> paymentResDtos = adminPaymentApiRepository.findAll(pageable).map(AdminPaymentResDto::new);

        return paymentResDtos;
    }
}
