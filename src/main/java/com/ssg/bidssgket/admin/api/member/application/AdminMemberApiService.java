package com.ssg.bidssgket.admin.api.member.application;

import com.ssg.bidssgket.admin.api.member.controller.dto.res.AdminMemberResDto;
import com.ssg.bidssgket.admin.api.member.repository.AdminMemberApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberApiService {

    private final AdminMemberApiRepository adminMemberApiRepository;

    /**
     * 회원 목록 조회
     */
    public Page<AdminMemberResDto> getUserList(Pageable pageable){

        Page<AdminMemberResDto> adminMemberResDtos = adminMemberApiRepository.findAll(pageable).map(AdminMemberResDto::new);

        return adminMemberResDtos;
    }

    /**
     * 회
     */
}
