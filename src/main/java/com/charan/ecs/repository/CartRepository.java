package com.charan.ecs.repository;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    String findAllByProductIdQueryString = "select c from Cart c where c.productIds like '%:productId%' ";
    Optional<Cart> findByCustomerId(int customerId);

    @Query(value = findAllByProductIdQueryString)
    List<Cart> findAllByProductId(int productId);
}
