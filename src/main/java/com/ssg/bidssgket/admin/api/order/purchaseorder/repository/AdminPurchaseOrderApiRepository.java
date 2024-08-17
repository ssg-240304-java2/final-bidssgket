package com.ssg.bidssgket.admin.api.order.purchaseorder.repository;

import com.ssg.bidssgket.user.domain.member.domain.Member;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminPurchaseOrderApiRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findAllByMember(Member member);
}
