package com.ssg.bidssgket.user.domain.product.application;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.member.domain.repository.MemberRepository;
import com.ssg.bidssgket.user.domain.product.domain.Acceptance;
import com.ssg.bidssgket.user.domain.product.domain.Product;
import com.ssg.bidssgket.user.domain.product.domain.ProductReport;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductReportRepository;
import com.ssg.bidssgket.user.domain.product.domain.repository.ProductRepository;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReportReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductReportService {

    private final ProductReportRepository productReportRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    public void submitProductReport(ProductReportReqDto reportReqDto) {
        Long memberNo = reportReqDto.getMemberNo();
        Member member = memberRepository.findById(memberNo).orElseThrow(EntityNotFoundException::new);
        Long productNo = reportReqDto.getProductNo();
        Product product = productRepository.findById(productNo).orElseThrow(EntityNotFoundException::new);

        ProductReport productReport = ProductReport.builder()
                                                    .complainContent(reportReqDto.getComplainContent())
                                                    .complainDate(LocalDateTime.now())
                                                    .acceptance(Acceptance.waiting)
                                                    .member(member)
                                                    .product(product)
                                                    .build();
        productReportRepository.save(productReport);
    }
}
