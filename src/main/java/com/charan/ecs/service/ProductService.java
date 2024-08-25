package com.charan.ecs.service;

import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.entity.Product;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.ProductMapper;
import com.charan.ecs.repository.ProductRepository;
import com.charan.ecs.service.interfaces.*;
import com.charan.ecs.util.Constants;
import com.charan.ecs.validations.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryServiceInterface productCategoryServiceInterface;
    @Autowired
    private ProductBrandServiceInterface productBrandServiceInterface;
    @Autowired
    private RemoveDependencies removeDependencies;

    @Override
    public ProductFinalDto getProduct(Integer productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
        return ProductMapper.mapToProductFinalDto(product, productCategoryServiceInterface, productBrandServiceInterface);
    }

    @Override
    public List<ProductFinalDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map((product) -> ProductMapper.
                mapToProductFinalDto(
                        product,
                        productCategoryServiceInterface,
                        productBrandServiceInterface)
                ).
                collect(Collectors.toList());
    }

    @Override
    public Object addProduct(ProductDto productDto) {
        boolean productExists = productRepository.existsById(productDto.getProductId());
        if(productExists) {
            return HttpStatus.CONFLICT;
        }
        return validateAndSaveOrUpdateProduct(productDto);
    }

    @Override
    public Object updateProduct(ProductFinalDto productFinalDto) {
        boolean productExists = productRepository.existsById(productFinalDto.getProductId());
        if(productExists) {
            return validateAndSaveOrUpdateProduct(ProductMapper.mapToProductDto(productFinalDto));
        }
        return Constants.ProductNotFound;
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        if(productId!=0 && productRepository.existsById(productId)){
            removeDependencies.deleteProductDependencies(productId);
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isProductExists(Integer productId) {
        return productRepository.existsById(productId);
    }

    private Object validateAndSaveOrUpdateProduct(ProductDto productDto) {
        if(!ProductValidation.isProductDtoSchemaValid(productDto)){
            return HttpStatus.BAD_REQUEST;
        }
        boolean productCategoryExists = productCategoryServiceInterface.
                isProductCategoryExists(productDto.getProductCategoryId());
        boolean productBrandExists = productBrandServiceInterface.isProductBrandExists(productDto.getProductBrandId());
        if(!productBrandExists){
            return Constants.ProductBrandNotFound;
        } else if (!productCategoryExists) {
            return Constants.ProductCategoryNotFound;
        } else {
            Product product = productRepository.
                    save(ProductMapper.mapToProduct(productDto));
            return ProductMapper.mapToProductFinalDto(product, productCategoryServiceInterface, productBrandServiceInterface);
        }
    }
}
