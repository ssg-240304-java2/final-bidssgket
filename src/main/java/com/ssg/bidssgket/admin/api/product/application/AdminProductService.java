package com.ssg.bidssgket.admin.api.product.application;

import com.ssg.bidssgket.admin.api.product.controller.dto.res.AdminProductResDto;
import com.ssg.bidssgket.admin.api.product.repository.AdminProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;


    public Page<AdminProductResDto> getProductList(Pageable pageable){
        Page<AdminProductResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductResDto::new);

        return productResDtos;
    }
}
