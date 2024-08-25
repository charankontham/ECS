package com.charan.ecs.service;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.dto.CartFinalDto;
import com.charan.ecs.entity.Cart;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.CartMapper;
import com.charan.ecs.repository.CartRepository;
import com.charan.ecs.service.interfaces.CartServiceInterface;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import com.charan.ecs.validations.CartValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerServiceInterface customerServiceInterface;

    @Override
    public CartFinalDto getCart(int cartId) {
        Cart cart = cartRepository.findById(cartId).
                orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return CartMapper.mapToCartFinalDto(cart, customerServiceInterface);
    }

    @Override
    public CartFinalDto getCartByCustomerId(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return CartMapper.mapToCartFinalDto(cart, customerServiceInterface);
    }

    @Override
    public List<CartDto> getCartsByProductId(int productId) {
        List<Cart> carts = cartRepository.findAllByProductId(productId);
        return carts.stream().map(CartMapper::mapToCartDto).collect(Collectors.toList());
    }

    @Override
    public Object addCart(CartDto cartDto) {
        boolean cartExists = cartRepository.existsById(cartDto.getCartId());
        if (cartExists) {
            return HttpStatus.CONFLICT;
        }
        return validateAndSaveCart(cartDto);
    }

    @Override
    public Object updateCart(CartDto cartDto) {
        boolean cartExists = cartRepository.existsById(cartDto.getCartId());
        if (cartExists) {
            return validateAndSaveCart(cartDto);
        }
        return Constants.CartNotFound;
    }

    @Override
    public boolean deleteCart(int cartId) {
        boolean cartExists = cartRepository.existsById(cartId);
        if (cartExists) {
            cartRepository.deleteById(cartId);
            return true;
        }
        return false;
    }

    private Object validateAndSaveCart(CartDto cartDto) {
        if (!CartValidation.validateCartRequestSchema(cartDto)) {
            return HttpStatus.BAD_REQUEST;
        }
        Object response = HelperFunctions.
                validateCustomerProductAndProductQuantities(
                        cartDto.getCustomerId(),
                        cartDto.getProductIds(),
                        cartDto.getProductQuantities()
                );
        if(Objects.equals(response, Constants.NoErrorFound)){
            Cart cart = cartRepository.save(CartMapper.mapToCart(cartDto));
            return CartMapper.mapToCartDto(cart);
        }
        return response;
    }
}
