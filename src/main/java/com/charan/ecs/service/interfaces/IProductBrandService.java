package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.ProductBrandDto;

import java.util.List;

public interface IProductBrandService {

    ProductBrandDto getProductBrandById(int brandId);

    List<ProductBrandDto> getAllProductBrands();

    Object addProductBrand(ProductBrandDto productBrandDto);

    Object updateProductBrand(ProductBrandDto productBrandDto);

    boolean deleteProductBrand(int brandId);

    boolean isProductBrandExists(int brandId);


}
