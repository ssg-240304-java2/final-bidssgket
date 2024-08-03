package com.ssg.bidssgket.user.domain.member.view.DTO;

import com.ssg.bidssgket.user.domain.member.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long memberNo;
    private String memberName;
    private String id;
    private String pwd;
    private String phone;
    private String memberNickname;
    private Integer buiscuit;
    private Boolean isDeleted;
    private Boolean isPenalty;
    private Address address;

}
