package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;

import java.util.List;

public interface ProductServiceInterface {

    ProductFinalDto getProduct(int productId);

    List<ProductFinalDto> getAllProducts();

    Object addProduct(ProductDto productDto);

    Object updateProduct(ProductFinalDto productFinalDto);

    boolean deleteProduct(int productId);

    boolean isProductExists(int productId);
}
