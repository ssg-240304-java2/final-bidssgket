package com.ssg.bidssgket.admin.api.payment.controller;


import com.ssg.bidssgket.admin.api.payment.application.AdminPaymentApiService;
import com.ssg.bidssgket.admin.api.payment.controller.dto.res.AdminPaymentResDto;
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
@RequestMapping("/admin/api/payment")
@RequiredArgsConstructor
public class AdminPaymentApiController {

    private final AdminPaymentApiService adminPaymentApiService;

    /**
     * 결제 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AdminPaymentResDto>> getPaymentList(@PageableDefault(size = 32, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get payment List ====");

        Page<AdminPaymentResDto> paymentList = adminPaymentApiService.getPaymentList(pageable);

        return new ResponseEntity<>(paymentList, HttpStatus.OK);
    }



}
