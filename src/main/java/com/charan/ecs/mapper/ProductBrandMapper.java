package com.charan.ecs.mapper;

import com.charan.ecs.dto.ProductBrandDto;
import com.charan.ecs.entity.ProductBrand;

public class ProductBrandMapper {
    public static ProductBrand mapToProductBrand(ProductBrandDto productBrandDto) {
        return new ProductBrand(
                productBrandDto.getBrandId(),
                productBrandDto.getBrandName(),
                productBrandDto.getBrandDescription()
        );
    }

    public static ProductBrandDto mapToProductBrandDto(ProductBrand productBrand) {
        return new ProductBrandDto(
                productBrand.getBrandId(),
                productBrand.getBrandName(),
                productBrand.getBrandDescription()
        );
    }
}
