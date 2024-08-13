package com.ssg.bidssgket.admin.api.order.purchaseorder.application;

import com.ssg.bidssgket.admin.api.order.purchaseorder.controller.dto.res.AdminPurchaseOrderResDto;
import com.ssg.bidssgket.admin.api.order.purchaseorder.repository.AdminPurchaseOrderApiRepository;
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
public class AdminPurchaseOrderApiService {

    private final AdminPurchaseOrderApiRepository adminPurchaseOrderApiRepository;


    public Page<AdminPurchaseOrderResDto> getPurchaseOrderList(Pageable pageable) {
        Page<AdminPurchaseOrderResDto> adminPurchaseOrderResDtos = adminPurchaseOrderApiRepository.findAll(pageable).map(AdminPurchaseOrderResDto::new);

        return adminPurchaseOrderResDtos;

    }
}
