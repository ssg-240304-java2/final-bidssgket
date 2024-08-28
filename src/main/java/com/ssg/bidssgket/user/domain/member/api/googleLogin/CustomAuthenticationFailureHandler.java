package com.ssg.bidssgket.user.domain.member.api.googleLogin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 로그인 실패 시 경고창을 띄우고 로그인 페이지로 리디렉션
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<script>alert('로그인에 실패했습니다. 다시 시도해 주세요.'); location.href='/login';</script>");
        response.getWriter().flush();
    }
}
