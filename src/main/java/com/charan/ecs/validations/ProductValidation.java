package com.charan.ecs.validations;

import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;

import java.util.Objects;

public class ProductValidation {
    public static boolean isProductFinalDtoSchemaValid(ProductFinalDto productFinalDto) {
        return Objects.nonNull(productFinalDto) &&
                Objects.nonNull(productFinalDto.getProductCategory().getCategoryId()) &&
                Objects.nonNull(productFinalDto.getBrand().getBrandId()) &&
                productFinalDto.getProductQuantity()>0 &&
                productFinalDto.getProductPrice()>0.00 &&
                BasicValidation.stringValidation(productFinalDto.getProductName());
    }

    public static boolean isProductDtoSchemaValid(ProductDto productDto) {
        return Objects.nonNull(productDto) &&
                Objects.nonNull(productDto.getProductBrandId()) &&
                productDto.getProductBrandId()!=0 &&
                Objects.nonNull(productDto.getProductCategoryId()) &&
                productDto.getProductCategoryId()!=0 &&
                productDto.getProductQuantity()>0 &&
                productDto.getProductPrice()>0.00 &&
                BasicValidation.stringValidation(productDto.getProductName());
    }
}
