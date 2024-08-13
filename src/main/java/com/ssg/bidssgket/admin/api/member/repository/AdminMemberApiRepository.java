package com.ssg.bidssgket.admin.api.member.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMemberApiRepository extends JpaRepository<Member, Long> {
}
