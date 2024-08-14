package com.charan.ecs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductBrandDto {
    private int brandId;
    private String brandName;
    private String brandDescription;
}
