package com.ssg.bidssgket.admin.api.order.purchaseorder.controller;


import com.ssg.bidssgket.admin.api.order.purchaseorder.application.AdminPurchaseOrderApiService;
import com.ssg.bidssgket.admin.api.order.purchaseorder.controller.dto.res.AdminPurchaseOrderResDto;
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
@RequestMapping("/admin/api/order")
@RequiredArgsConstructor
public class AdminPurchaseOrderApiController {

    private final AdminPurchaseOrderApiService adminPurchaseOrderApiService;
    /**
     * 구매 주문 내역 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/purchase/list")
    public ResponseEntity<Page<AdminPurchaseOrderResDto>> getPurchaseOrderList(@PageableDefault(size = 32, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get order List ====");

        Page<AdminPurchaseOrderResDto> purchaseOrderList = adminPurchaseOrderApiService.getPurchaseOrderList(pageable);

        return new ResponseEntity<>(purchaseOrderList, HttpStatus.OK);
    }





}
