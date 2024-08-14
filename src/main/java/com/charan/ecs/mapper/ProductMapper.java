package com.charan.ecs.mapper;

import com.charan.ecs.dto.ProductBrandDto;
import com.charan.ecs.dto.ProductCategoryDto;
import com.charan.ecs.dto.ProductDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.entity.Product;
import com.charan.ecs.entity.ProductBrand;
import com.charan.ecs.entity.ProductCategory;
import com.charan.ecs.repository.ProductCategoryRepository;
import com.charan.ecs.service.interfaces.ProductBrandServiceInterface;
import com.charan.ecs.service.interfaces.ProductCategoryServiceInterface;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import com.charan.ecs.util.HelperFunctions;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ProductMapper {
    private static ProductCategoryRepository productCategoryRepository;

    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductCategoryId(),
                product.getProductBrandId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductPrice(),
                product.getProductQuantity(),
                product.getProductColor(),
                product.getProductWeight(),
                product.getProductDimensions(),
                product.getProductCondition()
        );
    }

    public static Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getProductId(),
                productDto.getProductCategoryId(),
                productDto.getProductBrandId(),
                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getProductPrice(),
                productDto.getProductQuantity(),
                productDto.getProductColor(),
                productDto.getProductWeight(),
                productDto.getProductDimensions(),
                productDto.getProductCondition()
        );
    }

    public static ProductFinalDto mapToProductFinalDto(Product product,
                                                       ProductCategoryServiceInterface productCategoryServiceInterface,
                                                       ProductBrandServiceInterface productBrandServiceInterface) {
        ProductCategoryDto productCategoryDto = productCategoryServiceInterface.getProductCategoryById(product.getProductCategoryId());
        ProductBrandDto productBrandDto = productBrandServiceInterface.getProductBrandById(product.getProductBrandId());
        return new ProductFinalDto(
                product.getProductId(),
                product.getProductName(),
                productBrandDto,
                productCategoryDto,
                product.getProductDescription(),
                product.getProductPrice(),
                product.getProductQuantity(),
                product.getProductColor(),
                product.getProductWeight(),
                product.getProductDimensions(),
                product.getProductCondition()
        );
    }

    public static ProductDto mapToProductDto(ProductFinalDto productFinalDto) {
        return new ProductDto(
                productFinalDto.getProductId(),
                productFinalDto.getProductCategory().getCategoryId(),
                productFinalDto.getBrand().getBrandId(),
                productFinalDto.getProductName(),
                productFinalDto.getProductDescription(),
                productFinalDto.getProductPrice(),
                productFinalDto.getProductQuantity(),
                productFinalDto.getProductColor(),
                productFinalDto.getProductWeight(),
                productFinalDto.getProductDimensions(),
                productFinalDto.getProductCondition()
        );
    }

    public static List<ProductFinalDto> mapProductQuantitiesWithProductFinalDtoList(String productIds, String productQuantities, ProductServiceInterface productServiceInterface) {
        int count = 0;
        List<Integer> productIdsList = HelperFunctions.mapToIntegerArrayList(productIds);
        List<Integer> productQuantitiesList = HelperFunctions.mapToIntegerArrayList(productQuantities);
        List<ProductFinalDto> productFinalDtoList =  HelperFunctions.getProductFinalDtoList(productIdsList, productServiceInterface);
        for(ProductFinalDto productFinalDto : productFinalDtoList) {
            productFinalDto.setProductQuantity(productQuantitiesList.get(count));
        }
        return productFinalDtoList;
    }
}
