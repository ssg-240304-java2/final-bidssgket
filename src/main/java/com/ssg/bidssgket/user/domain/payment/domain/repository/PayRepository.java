package com.ssg.bidssgket.user.domain.payment.domain.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {
    // 추가로 필요한 쿼리 메서드가 있다면 여기에 정의

    Optional<Pay> findByMember(Member member);
}
