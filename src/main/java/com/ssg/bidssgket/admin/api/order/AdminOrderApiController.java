package com.ssg.bidssgket.admin.api.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/api/order")
@RequiredArgsConstructor
public class AdminOrderApiController {


    /**
     * 주문 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<String>> getUserList(@PageableDefault(size = 32, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        log.info("==== get order List ====");

//        Page<String> carList = carInfoService.getCarList(pageable);

//        return new ResponseEntity<>(carList, HttpStatus.OK);
        return null;
    }



}
