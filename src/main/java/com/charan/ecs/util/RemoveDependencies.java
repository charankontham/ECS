package com.charan.ecs.util;

import com.charan.ecs.service.interfaces.CartServiceInterface;
import com.charan.ecs.service.interfaces.OrderServiceInterface;
import com.charan.ecs.service.interfaces.ProductReviewServiceInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveDependencies {
    private static CartServiceInterface cartServiceInterface;
    private static OrderServiceInterface orderServiceInterface;
    private static ProductReviewServiceInterface productReviewServiceInterface;
    public static void deleteProductDependencies(Integer productId) {
        productReviewServiceInterface.deleteProductReviewsByProductId(productId);
        HelperFunctions.removeCartItemsByProductId(productId, cartServiceInterface);
        HelperFunctions.removeOrderItemsByProductId(productId, orderServiceInterface);
    }
}
