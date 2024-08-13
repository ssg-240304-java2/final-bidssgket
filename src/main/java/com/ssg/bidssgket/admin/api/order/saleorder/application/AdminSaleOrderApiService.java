package com.ssg.bidssgket.admin.api.order.saleorder.application;

import com.ssg.bidssgket.admin.api.order.saleorder.controller.dto.res.AdminSaleOrderResDto;
import com.ssg.bidssgket.admin.api.order.saleorder.repository.AdminSaleOrderApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminSaleOrderApiService {

    private final AdminSaleOrderApiRepository adminSaleOrderApiRepository;


    public Page<AdminSaleOrderResDto> getSaleOrderList(Pageable pageable) {
        Page<AdminSaleOrderResDto> adminSaleOrderResDtos = adminSaleOrderApiRepository.findAll(pageable).map(AdminSaleOrderResDto::new);

        return adminSaleOrderResDtos;
    }
}
