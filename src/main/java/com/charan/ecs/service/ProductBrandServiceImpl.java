package com.charan.ecs.service;

import com.charan.ecs.dto.ProductBrandDto;
import com.charan.ecs.entity.ProductBrand;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.ProductBrandMapper;
import com.charan.ecs.repository.ProductBrandRepository;
import com.charan.ecs.service.interfaces.IProductBrandService;
import com.charan.ecs.validations.BasicValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBrandServiceImpl implements IProductBrandService {

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Override
    public ProductBrandDto getProductBrandById(int brandId) {
        ProductBrand productBrand = productBrandRepository.findById(brandId).
                orElseThrow(() -> new ResourceNotFoundException("Product Brand Not Found!"));
        return ProductBrandMapper.mapToProductBrandDto(productBrand);
    }

    @Override
    public List<ProductBrandDto> getAllProductBrands() {
        List<ProductBrand> productBrands = productBrandRepository.findAll();
        return productBrands.stream().map(ProductBrandMapper::mapToProductBrandDto).
                collect(Collectors.toList());
    }

    @Override
    public Object addProductBrand(ProductBrandDto productBrandDto) {
        boolean productBrandExists = productBrandRepository.existsById(productBrandDto.getBrandId());
        if(!BasicValidation.stringValidation(productBrandDto.getBrandName())){
            return HttpStatus.BAD_REQUEST;
        }
        if(!productBrandExists) {
            ProductBrand productBrand = productBrandRepository.save(ProductBrandMapper.mapToProductBrand(productBrandDto));
            return ProductBrandMapper.mapToProductBrandDto(productBrand);
        }
        return HttpStatus.CONFLICT;
    }

    @Override
    public Object updateProductBrand(ProductBrandDto productBrandDto) {
        boolean productBrandExists = productBrandRepository.existsById(productBrandDto.getBrandId());
        if(!BasicValidation.stringValidation(productBrandDto.getBrandName())){
            return HttpStatus.BAD_REQUEST;
        }
        if(productBrandExists) {
            ProductBrand productBrand = productBrandRepository.save(ProductBrandMapper.mapToProductBrand(productBrandDto));
            return ProductBrandMapper.mapToProductBrandDto(productBrand);
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public boolean deleteProductBrand(int brandId) {
        boolean isDeleted = productBrandRepository.existsById(brandId);
        if(isDeleted){
            productBrandRepository.deleteById(brandId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isProductBrandExists(int brandId) {
        return productBrandRepository.existsById(brandId);
    }
}
