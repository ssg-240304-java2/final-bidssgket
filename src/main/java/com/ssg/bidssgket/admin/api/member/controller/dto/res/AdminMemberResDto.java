package com.ssg.bidssgket.admin.api.member.controller.dto.res;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMemberResDto {
    private Long memberNo;
    private String memberNickname;
    private String email;
//    private String address;
    private String role;
    private Integer biscuit;
    private String isDeleted;
    private String isPenalty;

    public AdminMemberResDto(Member member) {
        this.memberNo = member.getMemberNo();
        this.memberNickname = member.getMemberNickname();
        this.email = member.getEmail();
//        this.address = member.getAddress().toString();
        this.role = member.getRole();
        this.biscuit = member.getBiscuit();
        this.isDeleted = member.isDeleted() == true ? "YES" : "NO";
        this.isPenalty = member.isPenalty() == true ? "YES" : "NO";
    }
}
