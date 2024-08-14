package com.charan.ecs.controller;

import com.charan.ecs.dto.ProductCategoryDto;
import com.charan.ecs.entity.ProductCategory;
import com.charan.ecs.service.interfaces.ProductCategoryServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryController {
    private final ProductCategoryServiceInterface productCategoryServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCategoryById(@PathVariable("id") int categoryId) {
        ProductCategoryDto productCategoryDto = productCategoryServiceInterface.getProductCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryDto);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProductCategory() {
        List<ProductCategoryDto> productCategories = productCategoryServiceInterface.getAllProductCategories();
        return ResponseEntity.ok(productCategories);
    }

    @PostMapping
    public ResponseEntity<?> addProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategoryDto newProductCategoryDto = productCategoryServiceInterface.addProductCategory(productCategoryDto);
        if(!Objects.nonNull(newProductCategoryDto)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Incorrect request body!");
        }
        return new ResponseEntity<>(newProductCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategoryDto newProductCategoryDto = productCategoryServiceInterface.updateProductCategory(productCategoryDto);
        if(!Objects.nonNull(newProductCategoryDto)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CategoryId not found!");
        }
        return new ResponseEntity<>(newProductCategoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable("id") int productCategoryId) {
        boolean isDeleted = productCategoryServiceInterface.deleteProductCategory(productCategoryId);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Product category deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product category not found!");
    }
}
