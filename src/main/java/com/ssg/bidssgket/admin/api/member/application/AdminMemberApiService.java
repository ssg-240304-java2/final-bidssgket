package com.ssg.bidssgket.admin.api.member.application;

import com.ssg.bidssgket.admin.api.member.controller.dto.res.AdminMemberInfoResDto;
import com.ssg.bidssgket.admin.api.member.controller.dto.res.AdminMemberResDto;
import com.ssg.bidssgket.admin.api.member.repository.AdminMemberApiRepository;
import com.ssg.bidssgket.admin.api.order.purchaseorder.repository.AdminPurchaseOrderApiRepository;
import com.ssg.bidssgket.admin.api.order.saleorder.repository.AdminSaleOrderApiRepository;
import com.ssg.bidssgket.admin.api.product.product.controller.dto.res.AdminProductResDto;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberApiService {

    private final AdminMemberApiRepository adminMemberApiRepository;
    private final AdminSaleOrderApiRepository adminSaleOrderApiRepository;
    private final AdminPurchaseOrderApiRepository adminPurchaseOrderApiRepository;

    /**
     * 회원 목록 조회
     */
    public Page<AdminMemberResDto> getUserList(Pageable pageable){

        Page<AdminMemberResDto> adminMemberResDtos = adminMemberApiRepository.findAll(pageable).map(AdminMemberResDto::new);

        return adminMemberResDtos;
    }

    /**
     * 회원 상세 조회
     */
    public AdminMemberInfoResDto getUserInfo(Long memberId) {

        //회원정보
        Member member = adminMemberApiRepository.findById(memberId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
        AdminMemberResDto adminMemberResDto = new AdminMemberResDto(member);

        //구매한 상품 목록
        List<AdminProductResDto> purchaseProductList = new ArrayList<>();
        adminPurchaseOrderApiRepository.findAllByMember(member)
                .stream()
                .forEach((purchaseOrder) -> {
            purchaseProductList.add(new AdminProductResDto(purchaseOrder.getProduct()));
        });


        //판매한 상품 목록
        List<AdminProductResDto> saleProductList = new ArrayList<>();
        adminSaleOrderApiRepository.findAllByMember(member)
                .stream()
                .forEach((purchaseOrder) -> {
                    saleProductList.add(new AdminProductResDto(purchaseOrder.getProduct()));
                });


        return new AdminMemberInfoResDto(adminMemberResDto, purchaseProductList, saleProductList);

    }


}
