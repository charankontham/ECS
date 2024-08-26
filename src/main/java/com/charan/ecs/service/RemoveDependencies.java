package com.charan.ecs.service;

import com.charan.ecs.repository.ProductReviewRepository;
import com.charan.ecs.service.interfaces.ICartService;
import com.charan.ecs.service.interfaces.IOrderService;
import com.charan.ecs.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveDependencies {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ProductReviewRepository productReviewRepository;

    public void deleteProductDependencies(Integer productId) {
        productReviewRepository.deleteByProductId(productId);
        HelperFunctions.removeCartItemsByProductId(productId, cartService);
        HelperFunctions.removeOrderItemsByProductId(productId, orderService);
    }
}
