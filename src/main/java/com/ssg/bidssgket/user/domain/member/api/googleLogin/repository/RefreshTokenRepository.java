package com.ssg.bidssgket.user.domain.member.api.googleLogin.repository;

import com.ssg.bidssgket.user.domain.member.api.googleLogin.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByMemberId(Long memberId);
}