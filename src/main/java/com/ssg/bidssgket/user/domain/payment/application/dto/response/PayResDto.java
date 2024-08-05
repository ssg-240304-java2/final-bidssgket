package com.ssg.bidssgket.user.domain.payment.application.dto.response;

import com.ssg.bidssgket.user.domain.payment.domain.Pay;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayResDto {
    private Long payNo;
    private int payBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    private Long memberNo;
    private List<PayChangeResDto> payChangeList;

    public PayResDto(Pay pay) {
        this.payNo = pay.getPayNo();
        this.payBalance = pay.getPayBalance();
        this.createdAt = pay.getCreatedAt();
        this.updatedAt = pay.getUpdatedAt();
        this.isDeleted = pay.getIsDeleted();

        this.memberNo = pay.getMember().getMemberNo();
        this.payChangeList = pay.getPayChangeList().stream()
                .map(PayChangeResDto::new)
                .collect(Collectors.toList());
    }
}