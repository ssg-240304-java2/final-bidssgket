package com.ssg.bidssgket.user.domain.payment.api.service;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.payment.api.repository.MemberTestRepository;
import com.ssg.bidssgket.user.domain.payment.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberTestService {

    @Autowired
    private MemberTestRepository memberTestRepository;

    /***
     * memberNo를 기준으로 회원 정보 조회
     */
    public Member getMember(Long memberNo) {

        Optional<Member> memberOptional = memberTestRepository.findById(memberNo);

        if (memberOptional.isPresent()) {

            return memberOptional.get();
        } else {

            return null;
        }
    }

    public Member getMemberByEmail(String email) {

        return (Member) memberTestRepository.getMemberByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + " : 회원 정보를 찾을 수 없습니다."));
    }
}
