package com.ssg.bidssgket.user.domain.product.domain;

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

//    @ManyToOne
//    @JoinColumn(name = "memberNo")
//    private Memeber memeberNo;
}
