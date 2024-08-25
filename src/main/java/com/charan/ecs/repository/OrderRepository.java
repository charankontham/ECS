package com.charan.ecs.repository;

import com.charan.ecs.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    String findAllByProductIdQueryString = "select o from Order o where o.productIds like '%:productId%' ";

    List<Order> findByCustomerId(int customerId);

    @Query(value = findAllByProductIdQueryString)
    List<Order> findAllByProductId(int productId);
}
