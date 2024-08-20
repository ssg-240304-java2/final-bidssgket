package com.ssg.bidssgket.user.domain.payment.api.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberTestRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 정보를 조회하는 메서드
    Optional<Member> getMemberByEmail(String email);
}
