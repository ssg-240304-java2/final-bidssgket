package com.ssg.bidssgket.user.domain.member.api.googleLogin.service;


import com.ssg.bidssgket.user.domain.member.api.googleLogin.repository.UserRepository;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.view.DTO.MemberDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(MemberDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(Member.builder()
                        .memberName(dto.getMemberName())
                        .memberId(dto.getMemberId())
                        .role(dto.getRole())
                        .memberNickname(dto.getMemberNickname())
                        .biscuit(dto.getBiscuit())
                        .isDeleted(dto.getIsDeleted())
                        .isPenalty(dto.getIsPenalty())
                .email(dto.getEmail())
                .pwd(encoder.encode(dto.getPwd()))
                .build()).getMemberNo();
    }

    public Member findById(Long memberNo) {
        return userRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Member findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}