package com.charan.ecs.controller;

import com.charan.ecs.dto.ProductBrandDto;
import com.charan.ecs.service.interfaces.ProductBrandServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/productBrand")
public class ProductBrandController {

    @Autowired
    private ProductBrandServiceInterface productBrandServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<ProductBrandDto> getProductBrandById(@PathVariable("id") int brandId) {
        ProductBrandDto productBrandDto = productBrandServiceInterface.getProductBrandById(brandId);
        return ResponseEntity.ok(productBrandDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductBrandDto>> getAllProductBrands() {
        List<ProductBrandDto> productBrands = productBrandServiceInterface.getAllProductBrands();
        return ResponseEntity.ok(productBrands);
    }

    @PostMapping
    public ResponseEntity<?> addProductBrand(@RequestBody ProductBrandDto productBrandDto) {
        Object response = productBrandServiceInterface.addProductBrand(productBrandDto);
        if(Objects.equals(response, HttpStatus.CONFLICT)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product brand already exists!");
        } else if (Objects.equals(response, HttpStatus.BAD_REQUEST)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed!");
        }
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProductBrand(@RequestBody ProductBrandDto productBrandDto) {
        Object response = productBrandServiceInterface.updateProductBrand(productBrandDto);
        if(Objects.equals(response, HttpStatus.NOT_FOUND)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBrand Not Found!");
        }else if (Objects.equals(response, HttpStatus.BAD_REQUEST)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed!");
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductBrand(@PathVariable("id") int brandId) {
        boolean response = productBrandServiceInterface.deleteProductBrand(brandId);
        if(response){
            return ResponseEntity.status(HttpStatus.OK).body("Product brand Deleted Successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBrand Not Found!");
    }
}
