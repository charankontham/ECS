package com.charan.ecs.repository;

import com.charan.ecs.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Integer> {
}
