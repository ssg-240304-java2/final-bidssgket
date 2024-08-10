package com.ssg.bidssgket.admin.api.member.controller;


import com.ssg.bidssgket.admin.api.member.application.AdminMemberApiService;
import com.ssg.bidssgket.admin.api.member.controller.dto.res.AdminMemberInfoResDto;
import com.ssg.bidssgket.admin.api.member.controller.dto.res.AdminMemberResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/api/member")
@RequiredArgsConstructor
public class AdminMemberApiController {

    private final AdminMemberApiService adminMemberApiService;

    /**
     * 회원 목록 조회
     * paging 처리(ajax)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AdminMemberResDto>> getUserList(@PageableDefault(size = 32, sort="memberNo", direction = Sort.Direction.ASC) Pageable pageable){

        log.info("==== get member List ====");

        Page<AdminMemberResDto> memberList = adminMemberApiService.getUserList(pageable);


        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/info/{memberId}")
    public ResponseEntity<AdminMemberInfoResDto> getUserInfo(@PathVariable(name = "memberId") Long memberId){

        AdminMemberInfoResDto memberInfo = adminMemberApiService.getUserInfo(memberId);

        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }



}
