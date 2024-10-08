package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.ProductReviewDto;

import java.util.List;

public interface ProductReviewServiceInterface {

    ProductReviewDto getProductReviewById(int reviewId);

    List<ProductReviewDto> getAllProductReviews();

    List<ProductReviewDto> getProductReviewsByProductId(int productId);

    List<ProductReviewDto> getProductReviewsByCustomerId(int customerId);

    ProductReviewDto getProductReviewByCustomerIdAndProductId(int productId, int customerId);

    Object addProductReview(ProductReviewDto productReviewDto);

    Object updateProductReview(ProductReviewDto productReviewDto);

    boolean deleteProductReviewById(int reviewId);

    void deleteProductReviewsByProductId(int productId);

    boolean deleteProductReviewByProductIdAndCustomerId(int productId, int customerId);

    boolean isProductReviewExists(int reviewId);
}
