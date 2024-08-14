package com.charan.ecs.validations;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.entity.Order;
import com.charan.ecs.util.HelperFunctions;

import java.util.Objects;

public class OrderValidation {
    public static boolean validateOrderRequestSchema(OrderDto orderDto) {
        return Objects.nonNull(orderDto) &&
                Objects.nonNull(orderDto.getProductIds()) &&
                Objects.nonNull(orderDto.getProductQuantities()) &&
                !orderDto.getProductIds().isEmpty() &&
                orderDto.getProductIds().size() == orderDto.getProductQuantities().size() &&
                HelperFunctions.checkZeroQuantities(orderDto.getProductQuantities()) &&
                HelperFunctions.checkDuplicatesInList(orderDto.getProductIds()) &&
                BasicValidation.stringValidation(orderDto.getShippingStatus()) &&
                BasicValidation.stringValidation(orderDto.getPaymentType()) &&
                BasicValidation.stringValidation(orderDto.getPaymentStatus());
    }
}
