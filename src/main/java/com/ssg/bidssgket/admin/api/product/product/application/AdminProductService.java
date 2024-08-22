package com.ssg.bidssgket.admin.api.product.product.application;

import com.ssg.bidssgket.admin.api.product.product.controller.dto.res.AdminProductResDto;
import com.ssg.bidssgket.admin.api.product.product.repository.AdminProductRepository;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;


    public Page<AdminProductResDto> getProductList(Pageable pageable){
        Page<AdminProductResDto> productResDtos = adminProductRepository.findAll(pageable).map(AdminProductResDto::new);

        return productResDtos;
    }

    public Long getProductSellerMemberNo(Long productNo) {
        Long memberNo = adminProductRepository.findById(productNo).orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다.")).getMember().getMemberNo();

        return memberNo;
    }
}
