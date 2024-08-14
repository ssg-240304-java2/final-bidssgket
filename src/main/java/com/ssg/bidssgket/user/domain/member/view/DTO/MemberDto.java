package com.ssg.bidssgket.user.domain.member.view.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long memberNo; // 사용자 고유번호
    private String memberName; // 사용자 이름
    private String memberId; // 아이디
    private String pwd; // 비밀번호
    private String email; // 이메일
    private String memberNickname; // 사용자 닉네임
    private Integer biscuit; // 비스킷 온도
    private Boolean isDeleted; // 탈퇴 여부
    private Boolean isPenalty; // 패널티 여부
    private AddressDto address; // 주소

    @Builder
    public MemberDto(Long memberNo, String memberName, String memberId, String pwd, String email, String memberNickname,
                     Integer biscuit, Boolean isDeleted, Boolean isPenalty, AddressDto address) {
        this.memberNo = memberNo;
        this.memberName = memberName;
        this.memberId = memberId;
        this.pwd = pwd;
        this.email = email;
        this.memberNickname = memberNickname;
        this.biscuit = biscuit;
        this.isDeleted = isDeleted;
        this.isPenalty = isPenalty;
        this.address = address;
    }
}
