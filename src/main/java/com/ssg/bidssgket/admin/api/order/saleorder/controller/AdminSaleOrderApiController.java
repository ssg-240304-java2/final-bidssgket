package com.ssg.bidssgket.admin.api.order.saleorder.controller;


import com.ssg.bidssgket.admin.api.order.saleorder.application.AdminSaleOrderApiService;
import com.ssg.bidssgket.admin.api.order.saleorder.controller.dto.res.AdminSaleOrderResDto;
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
@RequestMapping("/admin/api/order/sale")
@RequiredArgsConstructor
public class AdminSaleOrderApiController {

    private final AdminSaleOrderApiService adminSaleOrderApiService;

    /**
     * 판매 주문 내역 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AdminSaleOrderResDto>> getSaleOrderList(@PageableDefault(size = 32, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get sale order List ====");

        Page<AdminSaleOrderResDto> saleOrderList = adminSaleOrderApiService.getSaleOrderList(pageable);


        return new ResponseEntity<>(saleOrderList, HttpStatus.OK);
    }



}
