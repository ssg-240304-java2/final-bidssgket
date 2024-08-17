package com.ssg.bidssgket.admin.api.member.controller.dto.res;

import com.ssg.bidssgket.admin.api.product.product.controller.dto.res.AdminProductResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMemberInfoResDto {
    private AdminMemberResDto adminMemberResDto;
    private List<AdminProductResDto> purchaseProductList;
    private List<AdminProductResDto> saleProductList;

    public AdminMemberInfoResDto(AdminMemberResDto adminMemberResDto, List<AdminProductResDto> purchaseProductList, List<AdminProductResDto> saleProductList) {
        this.adminMemberResDto = adminMemberResDto;
        this.purchaseProductList = purchaseProductList;
        this.saleProductList = saleProductList;
    }

}
