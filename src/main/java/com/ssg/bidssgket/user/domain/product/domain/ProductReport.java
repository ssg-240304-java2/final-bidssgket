package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReportReqDto;
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

    @ManyToOne
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @Builder
    private ProductReport(String complainContent, LocalDateTime complainDate, Member member, Product product) {
        this.complainContent = complainContent;
        this.complainDate = complainDate;
        this.member = member;
        this.product = product;
    }

    public static ProductReport addProductReport(ProductReportReqDto productReportReqDto, Member member,Product product) {
        return ProductReport.builder()
                .complainContent(productReportReqDto.getComplainContent())
                .complainDate(productReportReqDto.getComplainDate())
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
}
