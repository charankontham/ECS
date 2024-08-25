package com.charan.ecs.mapper;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.dto.CartFinalDto;
import com.charan.ecs.entity.Cart;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import com.charan.ecs.util.HelperFunctions;

public class CartMapper {

    public static CartDto mapToCartDto(Cart cart) {
        System.out.println("List : "+ cart.getProductIds()+cart.getQuantities());
        return new CartDto(
                cart.getCartId(),
                cart.getCustomerId(),
                HelperFunctions.mapToIntegerArrayList(cart.getProductIds()),
                HelperFunctions.mapToIntegerArrayList(cart.getQuantities())
        );
    }

    public static Cart mapToCart(CartDto cartDto) {
        return new Cart(
                cartDto.getCartId(),
                cartDto.getCustomerId(),
                cartDto.getProductIds().toString(),
                cartDto.getProductQuantities().toString()
        );
    }

    public static CartFinalDto mapToCartFinalDto(Cart cart,
                                                 CustomerServiceInterface customerServiceInterface) {
        return new CartFinalDto(
                cart.getCartId(),
                customerServiceInterface.getCustomerById(cart.getCustomerId()),
                ProductMapper.mapProductQuantitiesWithProductFinalDtoList(
                        cart.getProductIds(),
                        cart.getQuantities())
        );
    }
}
