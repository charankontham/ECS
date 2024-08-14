package com.charan.ecs.service;

import com.charan.ecs.dto.ProductCategoryDto;
import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.entity.Product;
import com.charan.ecs.entity.ProductCategory;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.ProductCategoryMapper;
import com.charan.ecs.mapper.ProductMapper;
import com.charan.ecs.repository.CustomerRepository;
import com.charan.ecs.repository.ProductCategoryRepository;
import com.charan.ecs.repository.ProductRepository;
import com.charan.ecs.service.interfaces.ProductBrandServiceInterface;
import com.charan.ecs.service.interfaces.ProductCategoryServiceInterface;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final ProductCategoryServiceInterface productCategoryServiceInterface;
    private final ProductBrandServiceInterface productBrandServiceInterface;

    @Override
    public ProductFinalDto getProduct(int productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product not found with Id : " + productId));
        return ProductMapper.mapToProductFinalDto(product, productCategoryServiceInterface, productBrandServiceInterface);
    }

    @Override
    public List<ProductFinalDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map((product) -> ProductMapper.
                mapToProductFinalDto(product,productCategoryServiceInterface, productBrandServiceInterface)).
                collect(Collectors.toList());
    }

    @Override
    public ProductFinalDto addProduct(ProductDto productDto) {
        boolean isProductCategoryExists = productCategoryServiceInterface.
                productCategoryExists(productDto.getProductCategoryId());
        if(isProductCategoryExists){
            Product product = productRepository.save(ProductMapper.mapToProduct(productDto));
            return ProductMapper.mapToProductFinalDto(product, productCategoryServiceInterface, productBrandServiceInterface);
        }
        return null;
    }

    @Override
    public ProductFinalDto updateProduct(ProductFinalDto productFinalDto) {
        boolean productCategoryExists = productCategoryServiceInterface.
                productCategoryExists(productFinalDto.getProductCategory().getCategoryId());
        if(productCategoryExists && productRepository.existsById(productFinalDto.getProductId())){
            Product product = productRepository.
                    save(ProductMapper.mapToProduct(ProductMapper.mapToProductDto(productFinalDto)));
            return ProductMapper.mapToProductFinalDto(product, productCategoryServiceInterface, productBrandServiceInterface);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(int productId) {
        if(productId!=0 && productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isProductExists(int productId) {
        return productRepository.existsById(productId);
    }
}
