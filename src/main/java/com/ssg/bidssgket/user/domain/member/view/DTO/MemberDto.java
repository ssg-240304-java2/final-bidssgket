package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private String memberName;
    private String memberId;
    private String pwd;
    private String email;
    private String role;
    private String memberNickname;
    private Integer biscuit;
    private Boolean isDeleted;
    private Boolean isPenalty;
    private Address address;


}
