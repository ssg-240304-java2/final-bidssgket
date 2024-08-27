package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long memberNo; // 사용자 고유번호
    private String memberName; // 사용자 이름
    private String pwd;
    private String memberId; // 아이디
    private String phone; // 휴대폰 번호
    private String email; // 이메일
    private String memberNickname; // 사용자 닉네임
    private Integer biscuit; // 비스킷 온도
    private Boolean isDeleted; // 탈퇴 여부
    private Boolean isPenalty; // 패널티 여부
    private AddressDto address; // 주소

    @Builder
    public MemberDto(Long memberNo, String memberName, String memberId, String pwd,String phone, String email, String memberNickname,
                     Integer biscuit, Boolean isDeleted, Boolean isPenalty, AddressDto address) {
        this.memberNo = memberNo;
        this.memberName = memberName;
        this.memberId = memberId;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
        this.memberNickname = memberNickname;
        this.biscuit = biscuit;
        this.isDeleted = isDeleted;
        this.isPenalty = isPenalty;
        this.address = address;
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(this.memberName)
                .memberId(this.memberId)
                .pwd(this.pwd)
                .phone(this.phone)
                .memberNickname(this.memberNickname)
                .email(this.email)
                .biscuit(this.biscuit)
                .address(this.address != null ? this.address.toEntity() : null)
                .isDeleted(this.isDeleted != null ? this.isDeleted : false)
                .isPenalty(this.isPenalty != null ? this.isPenalty : false)
                .role(Role.MEMBER)
                .build();
    }
}
