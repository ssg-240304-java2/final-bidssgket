package com.ssg.bidssgket.admin.api.order.saleorder.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdminSaleOrderApiRepository extends JpaRepository<SaleOrder, Long> {
    List<SaleOrder> findAllByMember(Member member);
}
