package com.ssg.bidssgket.user.domain.member.api.googleLogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
