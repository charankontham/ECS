package com.charan.ecs.validations;

import com.charan.ecs.dto.ProductReviewDto;
import com.charan.ecs.entity.ProductReview;

import java.util.Objects;

public class ProductReviewValidation {
    public static boolean validateProductReview(ProductReviewDto productReviewDto) {
        try {
            return Objects.nonNull(productReviewDto) &&
                    productReviewDto.getProductId() != 0 &&
                    productReviewDto.getCustomerId() != 0;
        }catch(Exception exception){
            return false;
        }

    }
}
