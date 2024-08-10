package com.ssg.bidssgket.admin.api.product.product.controller;


import com.ssg.bidssgket.admin.api.product.product.application.AdminProductService;
import com.ssg.bidssgket.admin.api.product.product.controller.dto.res.AdminProductResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/api/product")
@RequiredArgsConstructor
public class AdminProductApiController {

    private final AdminProductService adminProductService;
    /**
     * 상품 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AdminProductResDto>> getProductList(@PageableDefault(size = 32, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get product List ====");

        Page<AdminProductResDto> productList = adminProductService.getProductList(pageable);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


}
