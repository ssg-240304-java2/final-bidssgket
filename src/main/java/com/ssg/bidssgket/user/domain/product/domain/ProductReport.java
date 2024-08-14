package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ProductReport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complainNo;
    private String complainContent;
    private LocalDateTime complainDate;

    @Enumerated(EnumType.STRING)
    private Acceptance acceptance;

    @ManyToOne
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    private ProductReport(String complainContent, LocalDateTime complainDate,Acceptance acceptance , Member member, Product product) {
        this.complainContent = complainContent;
        this.complainDate = complainDate;
        this.acceptance = acceptance;
        this.member = member;
        this.product = product;
    }

    public static ProductReport addProductReport(ProductReport productReportReqDto, Member member, Product product) {
        return ProductReport.builder()
                .complainContent(productReportReqDto.getComplainContent())
                .complainDate(productReportReqDto.getComplainDate())
                .acceptance(productReportReqDto.getAcceptance())
                .member(member)
                .product(product)
                .build();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComplainContent(String complainContent) {
        this.complainContent = complainContent;
    }

    public void setComplainDate(LocalDateTime complainDate) {
        this.complainDate = complainDate;
    }

    public void setAcceptance(Acceptance acceptance) {
        this.acceptance = acceptance;
    }
}
