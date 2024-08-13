package com.ssg.bidssgket.admin.api.product.productreport.controller;


import com.ssg.bidssgket.admin.api.product.productreport.application.AdminProductReportService;
import com.ssg.bidssgket.admin.api.product.productreport.controller.dto.res.AdminProductReportResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/api/product/report")
@RequiredArgsConstructor
public class AdminProductReportApiController {

    private final AdminProductReportService adminProductService;

    /**
     * 상품 신고 대기 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/waiting/list")
    public ResponseEntity<Page<AdminProductReportResDto>> getWaitingProductReportList(@PageableDefault(size = 32, sort="complainDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get waiting product report List ====");

        Page<AdminProductReportResDto> productReportList = adminProductService.getWaitingProductReportList(pageable);

        return new ResponseEntity<>(productReportList, HttpStatus.OK);
    }


    /**
     * 상품 신고 승인 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/approval/list")
    public ResponseEntity<Page<AdminProductReportResDto>> getApproveProductReportList(@PageableDefault(size = 32, sort="complainDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get approval product report List ====");

        Page<AdminProductReportResDto> productReportList = adminProductService.getApprovalProductReportList(pageable);

        return new ResponseEntity<>(productReportList, HttpStatus.OK);
    }

    /**
     * 상품 신고 반려 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/rejection/list")
    public ResponseEntity<Page<AdminProductReportResDto>> getRejectProductReportList(@PageableDefault(size = 32, sort="complainDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get rejection product report List ====");

        Page<AdminProductReportResDto> productReportList = adminProductService.getRejectionProductReportList(pageable);

        return new ResponseEntity<>(productReportList, HttpStatus.OK);
    }


    /**
     * 상품 신고 승인
     */
    @PutMapping("/approval/{complainNo}")
    public ResponseEntity<String> approveProductReport(@PathVariable(name = "complainNo") Long complainNo){
        adminProductService.approveProductReport(complainNo);

        return new ResponseEntity<>("성공적으로 승인했습니다..", HttpStatus.OK);
    }


    /**
     * 상품 신고 반려
     *
     */
    @PutMapping("/rejection/{complainNo}")
    public ResponseEntity<String> rejectProductReport(@PathVariable(name = "complainNo") Long complainNo){
        adminProductService.rejectProductReport(complainNo);

        return new ResponseEntity<>("성공적으로 반려했습니다.", HttpStatus.OK);
    }



}
