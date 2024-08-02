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
    private int memberNo; // 회원 번호 [PK]

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_no")
    private Member member; // 회원 번호 [FK]를 PK로 사용

    @Column(name = "pay_balance", nullable = false)
    private int payBalance; // 비스킷 페이 잔액

    @OneToMany(mappedBy = "pay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayChange> payChanges = new ArrayList<>();

    @Builder
    public Pay(Member member, int payBalance) {
        this.member = member;
        this.memberNo = member.getMemberNo();
        this.payBalance = payBalance;
    }

    public void addPayChange(PayChange payChange) {
        payChange.setPay(this);
        this.payChanges.add(payChange);
    }
}
