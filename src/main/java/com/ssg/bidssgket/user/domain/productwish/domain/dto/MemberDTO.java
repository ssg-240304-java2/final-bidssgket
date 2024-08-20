package com.ssg.bidssgket.user.domain.productwish.domain.dto;


import com.ssg.bidssgket.user.domain.member.domain.Address;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberDTO {
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
    private Address address; // 주소

    @Builder
    public MemberDTO(Long memberNo, String memberName, String pwd, String memberId, String phone, String email, String memberNickname, Integer biscuit, Boolean isDeleted, Boolean isPenalty, Address address) {
        this.memberNo = memberNo;
        this.memberName = memberName;
        this.pwd = pwd;
        this.memberId = memberId;
        this.phone = phone;
        this.email = email;
        this.memberNickname = memberNickname;
        this.biscuit = biscuit;
        this.isDeleted = isDeleted;
        this.isPenalty = isPenalty;
        this.address = address;
    }
}
