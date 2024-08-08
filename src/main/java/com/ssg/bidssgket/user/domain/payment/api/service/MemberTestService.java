package com.ssg.bidssgket.user.domain.payment.api.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.repository.MemberTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberTestService {

    @Autowired
    private MemberTestRepository memberTestRepository;

    public Member getMember(Long memberNo) {

        Optional<Member> memberOptional = memberTestRepository.findById(memberNo);

        if (memberOptional.isPresent()) {

            return memberOptional.get();
        } else {

            return null;
        }
    }
}
