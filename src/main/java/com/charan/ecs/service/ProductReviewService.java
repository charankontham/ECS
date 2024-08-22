package com.charan.ecs.service;

import com.charan.ecs.dto.ProductReviewDto;
import com.charan.ecs.entity.ProductReview;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.ProductReviewMapper;
import com.charan.ecs.repository.ProductReviewRepository;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import com.charan.ecs.service.interfaces.OrderServiceInterface;
import com.charan.ecs.service.interfaces.ProductReviewServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import com.charan.ecs.validations.ProductReviewValidation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter
@Getter
public class ProductReviewService implements ProductReviewServiceInterface {

    private ProductReviewRepository productReviewRepository;
    private CustomerServiceInterface customerServiceInterface;
    private OrderServiceInterface orderServiceInterface;

    @Override
    public ProductReviewDto getProductReviewById(int reviewId) {
        ProductReview productReview = productReviewRepository.findById(reviewId).
                orElseThrow(() -> new ResourceNotFoundException("ProductReview Not Found!"));
        return ProductReviewMapper.mapToProductReviewDto(productReview);
    }

    @Override
    public List<ProductReviewDto> getAllProductReviews() {
        List<ProductReview> productReviews = productReviewRepository.findAll();
        return productReviews.stream().map(ProductReviewMapper::mapToProductReviewDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDto> getProductReviewsByProductId(int productId) {
        List<ProductReview> productReviews = productReviewRepository.findAllByProductId(productId);
        return productReviews.stream().map(ProductReviewMapper::mapToProductReviewDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDto> getProductReviewsByCustomerId(int customerId) {
        List<ProductReview> productReviews = productReviewRepository.findAllByCustomerId(customerId);
        return productReviews.stream().map(ProductReviewMapper::mapToProductReviewDto).collect(Collectors.toList());
    }

    @Override
    public ProductReviewDto getProductReviewByCustomerIdAndProductId(int productId, int customerId) {
        ProductReview productReview = productReviewRepository.
                findByProductIdAndCustomerId(productId, customerId).
                orElseThrow(() -> new ResourceNotFoundException("ProductReview Not Found!"));
        return ProductReviewMapper.mapToProductReviewDto(productReview);
    }

    @Override
    public Object addProductReview(ProductReviewDto productReviewDto) {
        boolean productReviewExists = productReviewRepository.existsById(productReviewDto.getReviewId());
        if (!productReviewExists) {
            try {
                return validateAndSaveProductReview(productReviewDto);
            } catch (DataIntegrityViolationException e) {
                System.out.println("DataIntegrity Exception message : " + e.getMessage());
            }
        }
        return HttpStatus.CONFLICT;
    }

    @Override
    public Object updateProductReview(ProductReviewDto productReviewDto) {
        boolean productReviewExists = productReviewRepository.existsById(productReviewDto.getReviewId());
        if (productReviewExists) {
            try {
                return validateAndSaveProductReview(productReviewDto);
            }catch (DataIntegrityViolationException e) {
                System.out.println("Exception message : " + e.getMessage());
                return HttpStatus.CONFLICT;
            }
        }
        return Constants.ProductReviewNotFound;
    }

    @Override
    public boolean deleteProductReviewById(int reviewId) {
        boolean productReviewExists = productReviewRepository.existsById(reviewId);
        if (productReviewExists) {
            productReviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public void deleteProductReviewsByProductId(int productId) {
        productReviewRepository.deleteByProductId(productId);
    }

    @Override
    public boolean deleteProductReviewByProductIdAndCustomerId(int productId, int customerId) {
        boolean productReviewExists = productReviewRepository.existsByProductIdAndCustomerId(productId, customerId);
        if (productReviewExists) {
            productReviewRepository.deleteByProductIdAndCustomerId(productId, customerId);
            return true;
        }
        return false;
    }

    @Override
    public boolean productReviewExists(int reviewId) {
        return productReviewRepository.existsById(reviewId);
    }

    @Override
    public Object validateAndSaveProductReview(ProductReviewDto productReviewDto) throws DataIntegrityViolationException {
        boolean customerExists = customerServiceInterface.isCustomerExist(productReviewDto.getCustomerId());
        boolean productExists = HelperFunctions.isProductExists(productReviewDto.getProductId());
        boolean orderExists = HelperFunctions.isOrderExistsByProductId(
                productReviewDto.getProductId(),
                orderServiceInterface.getAllOrdersByCustomerId(productReviewDto.getCustomerId())
        );
        if (!ProductReviewValidation.validateProductReview(productReviewDto)) {
            return HttpStatus.BAD_REQUEST;
        } else if (!customerExists) {
            return Constants.CustomerNotFound;
        } else if (!productExists) {
            return Constants.ProductNotFound;
        } else if (!orderExists) {
            return Constants.OrderNotFound;
        } else {
                ProductReview productReview = productReviewRepository.save(ProductReviewMapper.mapToProductReview(productReviewDto));
                return ProductReviewMapper.mapToProductReviewDto(productReview);
        }
    }
}
