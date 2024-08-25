package com.charan.ecs.service;

import com.charan.ecs.repository.ProductReviewRepository;
import com.charan.ecs.service.interfaces.CartServiceInterface;
import com.charan.ecs.service.interfaces.OrderServiceInterface;
import com.charan.ecs.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveDependencies {
    @Autowired
    private CartServiceInterface cartServiceInterface;
    @Autowired
    private OrderServiceInterface orderServiceInterface;
    @Autowired
    private ProductReviewRepository productReviewRepository;

    public void deleteProductDependencies(Integer productId) {
        productReviewRepository.deleteByProductId(productId);
        HelperFunctions.removeCartItemsByProductId(productId, cartServiceInterface);
        HelperFunctions.removeOrderItemsByProductId(productId, orderServiceInterface);
    }
}
