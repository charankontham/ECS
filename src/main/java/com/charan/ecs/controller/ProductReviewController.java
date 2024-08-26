package com.charan.ecs.controller;

import com.charan.ecs.dto.ProductReviewDto;
import com.charan.ecs.service.interfaces.IProductReviewService;
import com.charan.ecs.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productReview")
public class ProductReviewController {

    @Autowired
    private IProductReviewService productReviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewDto> getProductReviewById(@PathVariable("id") int reviewId) {
        ProductReviewDto productReviewDto = productReviewService.getProductReviewById(reviewId);
        return ResponseEntity.ok(productReviewDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductReviewDto>> getAllProductReviews() {
        List<ProductReviewDto> reviews = productReviewService.getAllProductReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getReviewsByProductId/{id}")
    public ResponseEntity<List<ProductReviewDto>> getProductReviewsByProductId(@PathVariable("id") int productId) {
        List<ProductReviewDto> reviews = productReviewService.getProductReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getReviewsByCustomerId/{id}")
    public ResponseEntity<List<ProductReviewDto>> getProductReviewsByCustomerId(@PathVariable("id") int customerId) {
        List<ProductReviewDto> reviews = productReviewService.getProductReviewsByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getReviewByProductIdAndCustomerId/{productId}/{customerId}")
    public ResponseEntity<ProductReviewDto> getProductReviewByProductIdAndCustomerId(@PathVariable("productId") int productId, @PathVariable("customerId") int customerId) {
        ProductReviewDto productReviewDto = productReviewService.getProductReviewByCustomerIdAndProductId(productId, customerId);
        return ResponseEntity.ok(productReviewDto);
    }

    @PostMapping
    public ResponseEntity<?> addProductReview(@RequestBody ProductReviewDto productReviewDto) {
        Object response = productReviewService.addProductReview(productReviewDto);
        ResponseEntity<?> responseEntity = HelperFunctions.getResponseEntity(response);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<?> updateProductReview(@RequestBody ProductReviewDto productReviewDto) {
        Object response = productReviewService.updateProductReview(productReviewDto);
        return HelperFunctions.getResponseEntity(response);
    }

    @DeleteMapping("/deleteByProductIdAndCustomerId/{productId}/{customerId}")
    public ResponseEntity<String> deleteProductReviewByProductIdAndCustomerId(@PathVariable("productId") int productId, @PathVariable("customerId") int customerId) {
        boolean isDeleted = productReviewService.deleteProductReviewByProductIdAndCustomerId(productId, customerId);
        if(isDeleted){
            return new ResponseEntity<>("ProductReview Deleted Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("ProductReview Not found!", HttpStatus.NOT_FOUND);
    }

}
