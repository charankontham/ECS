package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.ProductCategoryDto;

import java.util.List;

public interface IProductCategoryService {

    ProductCategoryDto getProductCategoryById(int categoryId);

    List<ProductCategoryDto> getAllProductCategories();

    ProductCategoryDto addProductCategory(ProductCategoryDto productCategoryDto);

    ProductCategoryDto updateProductCategory(ProductCategoryDto productCategoryDto);

    boolean deleteProductCategory(int categoryId);

    boolean isProductCategoryExists(int categoryId);
}
