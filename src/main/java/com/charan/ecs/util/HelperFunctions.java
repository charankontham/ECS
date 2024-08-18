package com.charan.ecs.util;

import com.charan.ecs.dto.*;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.service.interfaces.AddressServiceInterface;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.stream.Collectors;

public class HelperFunctions {

    public static boolean checkZeroQuantities(List<Integer> quantities) {
        for (Integer quantity : quantities) {
            if (quantity == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDuplicatesInList(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        return set.size() == list.size();
    }

    public static List<Integer> mapToIntegerArrayList(String str) {
        return Arrays.stream(str.
                        replaceAll("[ \\[\\]]", "").
                        split(",")).
                map(Integer::parseInt).
                collect(Collectors.toList());
    }

    public static List<Float> mapToDecimalArrayList(String str) {
        return Arrays.stream(str.
                        replaceAll("[ \\[\\]]", "").
                        split(",")).
                map(Float::parseFloat).
                collect(Collectors.toList());
    }

    public static List<ProductFinalDto> getProductFinalDtoList(List<Integer> productIdsList, ProductServiceInterface productServiceInterface) {
        return productIdsList.stream().map(productServiceInterface::getProduct).toList();
    }

    public static Object validateCustomerProductAndProductQuantities(int customerId,
                                                                     List<Integer> productIds,
                                                                     List<Integer> productQuantities,
                                                                     ProductServiceInterface productServiceInterface,
                                                                     CustomerServiceInterface customerServiceInterface) {
        try {
            CustomerDto customerDto = customerServiceInterface.getCustomerById(customerId);
            List<ProductFinalDto> productFinalDtoList = HelperFunctions.
                    getProductFinalDtoList(productIds, productServiceInterface);
            int count = 0;
            for (ProductFinalDto productFinalDto : productFinalDtoList) {
                if (productQuantities.get(count++) > productFinalDto.getProductQuantity()) {
                    throw new RuntimeException("Product Quantity Exceeded!");
                }
            }
            return Constants.NoErrorFound;
        } catch (ResourceNotFoundException e) {
            if (e.getMessage().contains("Customer")) {
                return Constants.CustomerNotFound;
            }
            if (e.getMessage().contains("Product")) {
                return Constants.ProductNotFound;
            }
            return HttpStatus.BAD_REQUEST;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Quantity Exceeded!")) {
                return Constants.ProductQuantityExceeded;
            } else {
                return HttpStatus.BAD_REQUEST;
            }
        }
    }

    public static Object validateAddress(int addressId, int customerId, AddressServiceInterface addressServiceInterface) {
        try {
            AddressDto addressDto = addressServiceInterface.getAddressById(addressId);
            if(addressDto.getCustomerId() != customerId) {
                return Constants.AddressNotFound;
            }
            return Constants.NoErrorFound;
        } catch (ResourceNotFoundException e) {
            if (e.getMessage().contains("Address")) {
                return Constants.AddressNotFound;
            }
            return e.getMessage();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public static boolean isOrderExistsByProductId(int productId, List<OrderFinalDto> orders) {
        return !orders.stream().
                filter((order) -> isProductExistsByProductId(productId, order.getProducts())).
                toList().isEmpty();
    }

    public static boolean isProductExistsByProductId(int productId, List<ProductFinalDto> products) {
        return !products.
                stream().
                filter((product) -> product.getProductId() == productId).
                toList().isEmpty();
    }

    public static ResponseEntity<?> getResponseEntity(Object response) {
        if (Objects.equals(response, Constants.ProductNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        } else if (Objects.equals(response, Constants.CustomerNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
        } else if (Objects.equals(response, Constants.ProductQuantityExceeded)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ProductQuantities Exceeded!");
        } else if (Objects.equals(response, Constants.AddressNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found!");
        } else if (Objects.equals(response, Constants.OrderNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        } else if (Objects.equals(response, Constants.CartNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found!");
        } else if (Objects.equals(response, Constants.UserNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else if (Objects.equals(response, Constants.ProductCategoryNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductCategory not found!");
        } else if (Objects.equals(response, Constants.ProductBrandNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBrand not found!");
        } else if (Objects.equals(response, Constants.ProductReviewNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductReview not found!");
        } else if (Objects.equals(response, HttpStatus.CONFLICT)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate Entry!");
        } else if (Objects.equals(response, HttpStatus.BAD_REQUEST)) {
            return new ResponseEntity<>("Validation Failed/Bad Request!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
