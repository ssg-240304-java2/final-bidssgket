package com.ssg.bidssgket.user.domain.payment.domain;

import com.ssg.bidssgket.common.domain.BaseTimeAndDeleteEntity;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pay")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseTimeAndDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payNo; // 페이 번호 [PK]

    @Column(name = "payBalance", nullable = false)
    private int payBalance; // 비스킷 페이 잔액

    @OneToOne
    @JoinColumn(name = "memberNo", nullable = false)
    private Member member; // 회원 번호 [FK]

    @OneToMany(mappedBy = "pay", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private final List<PayChange> payChangeList = new ArrayList<>();

    @Builder
    public Pay(Member member, int payBalance) {
        this.member = member;
        this.payBalance = payBalance;
    }

    public static Pay addPay(int payBalance, Member member) {
        return Pay.builder()
                .payBalance(payBalance)
                .member(member)
                .build();
    }

    /***
     * 회원 정보 설정 메서드
     * @param member 회원 정보
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /***
     * 비스킷 페이 변경 내역 추가
     * @param payChange 비스킷 페이 변경 내역
     */
    public void addPayChange(PayChange payChange) {
        payChange.setPay(this);
        this.payChangeList.add(payChange);
    }
}