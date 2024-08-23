package com.ssg.bidssgket.admin.api.product.productreport.repository;

import com.ssg.bidssgket.user.domain.product.domain.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProductReportRepository extends JpaRepository<ProductReport, Long> {


}
