package com.charan.ecs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int productId;
    private int productCategoryId;
    private int productBrandId;
    private String productName;
    private String productDescription;
    private float productPrice;
    private int productQuantity;
    private String productColor;
    private float productWeight;
    private String productDimensions;
    private String productCondition;
}
