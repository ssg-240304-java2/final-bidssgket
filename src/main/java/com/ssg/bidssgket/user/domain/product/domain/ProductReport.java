package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ProductReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complainNo;
    private int complainContent;
    private LocalDateTime complainDate;

    @ManyToOne
    @JoinColumn(name = "memberNo")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    public void setProductNo(Product productNo) {
        this.product = productNo;
    }
}
