package com.ssg.bidssgket.user.domain.member.api.googleLogin;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;

    public SessionMember(Member member) {
        this.name = member.getMemberNickname();
        this.email = member.getEmail();
    }
}
