package com.ssg.bidssgket.user.domain.payment.api.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberTestRepository extends JpaRepository<Member, Long> {
    Optional<Object> findByUsername(String username);
}
