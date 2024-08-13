package com.ssg.bidssgket.admin.api.product.productreport.repository;

import com.ssg.bidssgket.admin.api.product.productreport.controller.dto.res.AdminProductReportResDto;
import com.ssg.bidssgket.user.domain.product.domain.ProductReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProductReportRepository extends JpaRepository<ProductReport, Long> {

//    @Query(value = "select pr " +
//            "from ProductReport pr " +
//            "where acceptance = ")
//    Page<AdminProductReportResDto> findAllByAcceptance(Pageable pageable);
//
//    public Page<AdminProductReportResDto> getApprovalProductReportList(Pageable pageable);
//    }
//
//    public Page<AdminProductReportResDto> getRejectionProductReportList(Pageable pageable);

}
