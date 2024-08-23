package com.ssg.bidssgket.admin.api.product.productreport.application;

import com.ssg.bidssgket.admin.api.product.productreport.controller.dto.res.AdminProductReportResDto;
import com.ssg.bidssgket.admin.api.product.productreport.repository.AdminProductReportRepository;
import com.ssg.bidssgket.user.domain.product.domain.Acceptance;
import com.ssg.bidssgket.user.domain.product.domain.ProductReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminProductReportService {

    private final AdminProductReportRepository adminProductRepository;


    public Page<AdminProductReportResDto> getWaitingProductReportList(Pageable pageable){
        Page<AdminProductReportResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductReportResDto::new);


        Page<AdminProductReportResDto> adminProductReportResDtos = new PageImpl<>(
                productResDtos.stream()
                        .filter((report) -> report.getAcceptance().equals("대기"))
                        .collect(Collectors.toList()), productResDtos.getPageable(), productResDtos.getTotalElements()
        );

        return adminProductReportResDtos;
    }

    public Page<AdminProductReportResDto> getApprovalProductReportList(Pageable pageable){
        Page<AdminProductReportResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductReportResDto::new);


        Page<AdminProductReportResDto> adminProductReportResDtos = new PageImpl<>(
                productResDtos.stream()
                        .filter((report) -> report.getAcceptance().equals("승인"))
                        .collect(Collectors.toList()), productResDtos.getPageable(), productResDtos.getTotalElements()
        );

        return adminProductReportResDtos;
    }

    public Page<AdminProductReportResDto> getRejectionProductReportList(Pageable pageable){
        Page<AdminProductReportResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductReportResDto::new);

        Page<AdminProductReportResDto> adminProductReportResDtos = new PageImpl<>(
                productResDtos.stream()
                        .filter((report) -> report.getAcceptance().equals("반려"))
                        .collect(Collectors.toList()), productResDtos.getPageable(), productResDtos.getTotalElements()
        );

        return adminProductReportResDtos;
    }


    public void approveProductReport(Long complainNo) {
        ProductReport productReport = adminProductRepository.findById(complainNo).orElseThrow(() -> new RuntimeException("신고가 존재하지 않습니다."));

        productReport.setAcceptance(Acceptance.approval);
    }

    public void rejectProductReport(Long complainNo) {
        ProductReport productReport = adminProductRepository.findById(complainNo).orElseThrow(() -> new RuntimeException("신고가 존재하지 않습니다."));

        productReport.setAcceptance(Acceptance.rejection);

    }

}
