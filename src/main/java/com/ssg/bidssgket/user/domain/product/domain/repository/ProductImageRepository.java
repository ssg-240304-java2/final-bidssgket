package com.ssg.bidssgket.user.domain.product.domain.repository;

import com.ssg.bidssgket.user.domain.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
