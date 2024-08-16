package com.charan.ecs.controller;

import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServiceInterface productServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<ProductFinalDto> getProductById(@PathVariable("id") int productId) {
        ProductFinalDto productFinalDto = productServiceInterface.getProduct(productId);
        return ResponseEntity.ok(productFinalDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductFinalDto>> getAllProducts() {
        List<ProductFinalDto> products = productServiceInterface.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        Object newProduct = productServiceInterface.addProduct(productDto);
        if(Objects.equals(newProduct, HttpStatus.BAD_REQUEST)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed!");
        }
        if(Objects.isNull(newProduct)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductCategory not found!");
        }
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody ProductFinalDto productFinalDto) {
        Object updatedProduct = productServiceInterface.updateProduct(productFinalDto);
        if(Objects.equals(updatedProduct, HttpStatus.BAD_REQUEST)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed!");
        }
        if(Objects.isNull(updatedProduct)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product/productCategory not found!");
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int productId) {
        boolean isDeleted = productServiceInterface.deleteProduct(productId);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
    }
}
