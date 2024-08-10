package com.ssg.bidssgket.admin.api.product.productreport.application;

import com.ssg.bidssgket.admin.api.product.productreport.controller.dto.res.AdminProductReportResDto;
import com.ssg.bidssgket.admin.api.product.productreport.repository.AdminProductReportRepository;
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
public class AdminProductReportService {

    private final AdminProductReportRepository adminProductRepository;


    public Page<AdminProductReportResDto> getProductReportList(Pageable pageable){
        Page<AdminProductReportResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductReportResDto::new);

        return productResDtos;
    }

    public void deleteProductReport(Long complainNo) {
        adminProductRepository.deleteById(complainNo);

    }

}
