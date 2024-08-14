package com.charan.ecs.mapper;

import com.charan.ecs.dto.ProductCategoryDto;
import com.charan.ecs.entity.ProductCategory;

public class ProductCategoryMapper {
    public static ProductCategoryDto mapToProductCategoryDto(ProductCategory productCategory) {
        return new ProductCategoryDto(
                productCategory.getCategoryId(),
                productCategory.getProductCategoryName()
        );
    }
    public static ProductCategory mapToProductCategory(ProductCategoryDto productCategoryDto) {
        return new ProductCategory(
                productCategoryDto.getCategoryId(),
                productCategoryDto.getCategoryName()
        );
    }
}
