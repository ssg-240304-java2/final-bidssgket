package com.ssg.bidssgket.admin.api.product.repository;

import com.ssg.bidssgket.user.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProductRepository extends JpaRepository<Product, Long> {

}
