package com.charan.ecs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFinalDto {
    private int productId;
    private String productName;
    private ProductBrandDto brand;
    private ProductCategoryDto productCategory;
    private String productDescription;
    private float productPrice;
    private int productQuantity;
    private String productColor;
    private float productWeight;
    private String productDimensions;
    private String productCondition;
}
