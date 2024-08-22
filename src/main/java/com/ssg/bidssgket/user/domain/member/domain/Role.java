package com.ssg.bidssgket.user.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    MEMBER("ROLE_MEMBER", "사용자");

    private final String key;
    private final String title;
}
