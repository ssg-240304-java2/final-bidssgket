package com.ssg.bidssgket.user.domain.member.api.googleLogin.repository;



import com.ssg.bidssgket.user.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}