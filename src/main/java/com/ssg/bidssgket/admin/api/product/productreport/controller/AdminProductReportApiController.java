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
     * 상품 신고 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AdminProductReportResDto>> getProductReportList(@PageableDefault(size = 32, sort="complainDate", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get product report List ====");

        Page<AdminProductReportResDto> productReportList = adminProductService.getProductReportList(pageable);

        return new ResponseEntity<>(productReportList, HttpStatus.OK);
    }

    /**
     * 상품 신고 반려
     *
     */
    @DeleteMapping("/{complainNo}")
    public ResponseEntity<String> deleteProductReport(@PathVariable(name = "complainNo") Long complainNo){
        adminProductService.deleteProductReport(complainNo);

        return new ResponseEntity<>("성공적으로 삭제하였습니다.", HttpStatus.OK);
    }



}
