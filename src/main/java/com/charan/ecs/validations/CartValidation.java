package com.charan.ecs.validations;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.util.HelperFunctions;
import java.util.Objects;

public class CartValidation {
    public static boolean validateCartRequestSchema(CartDto cartDto) {
        return Objects.nonNull(cartDto) &&
                Objects.nonNull(cartDto.getProductIds()) &&
                Objects.nonNull(cartDto.getProductQuantities()) &&
                !cartDto.getProductIds().isEmpty() &&
                cartDto.getProductIds().size() == cartDto.getProductQuantities().size() &&
                HelperFunctions.checkZeroQuantities(cartDto.getProductQuantities()) &&
                HelperFunctions.checkDuplicatesInList(cartDto.getProductIds());
    }
}
