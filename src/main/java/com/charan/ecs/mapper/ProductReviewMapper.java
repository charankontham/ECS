package com.charan.ecs.mapper;

import com.charan.ecs.dto.ProductReviewDto;
import com.charan.ecs.entity.ProductReview;

public class ProductReviewMapper {

    public static ProductReview mapToProductReview(ProductReviewDto productReviewDto) {
        return new ProductReview(
                productReviewDto.getReviewId(),
                productReviewDto.getProductId(),
                productReviewDto.getCustomerId(),
                productReviewDto.getProductReview(),
                productReviewDto.getProductRating()
        );
    }

    public static ProductReviewDto mapToProductReviewDto(ProductReview productReview) {
        return new ProductReviewDto(
                productReview.getReviewId(),
                productReview.getProductId(),
                productReview.getCustomerId(),
                productReview.getProductReview(),
                productReview.getProductRating()
        );
    }
}
