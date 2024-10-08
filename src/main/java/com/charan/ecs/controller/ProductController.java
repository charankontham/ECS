package com.charan.ecs.controller;

import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServiceInterface productServiceInterface;

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
        Object response = productServiceInterface.addProduct(productDto);
        if(response instanceof ProductFinalDto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return HelperFunctions.getResponseEntity(response);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody ProductFinalDto productFinalDto) {
        Object response = productServiceInterface.updateProduct(productFinalDto);
        return HelperFunctions.getResponseEntity(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int productId) {
        boolean isDeleted = productServiceInterface.deleteProduct(productId);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
        }
        return HelperFunctions.getResponseEntity(Constants.ProductNotFound);
    }
}
