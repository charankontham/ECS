package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.dto.CartFinalDto;

import java.util.List;

public interface CartServiceInterface {

    CartFinalDto getCart(int cartId);

    CartFinalDto getCartByCustomerId(int customerId);

    Object addCart(CartDto cartDto);

    Object updateCart(CartDto cartDto);

    boolean deleteCart(int cartId);

    Object validateAndSaveCart(CartDto cartDto);

}
