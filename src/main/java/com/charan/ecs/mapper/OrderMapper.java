package com.charan.ecs.mapper;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import com.charan.ecs.entity.Order;
import com.charan.ecs.service.interfaces.IAddressService;
import com.charan.ecs.service.interfaces.ICustomerService;
import com.charan.ecs.util.HelperFunctions;

public class OrderMapper {

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getCustomerId(),
                order.getAddressId(),
                HelperFunctions.mapToIntegerArrayList(order.getProductIds()),
                HelperFunctions.mapToIntegerArrayList(order.getProductQuantities()),
                order.getPaymentType(),
                order.getPaymentStatus(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getShippingStatus()
        );
    }

    public static Order toOrder(OrderDto orderDto) {
        return new Order(
                orderDto.getOrderId(),
                orderDto.getCustomerId(),
                orderDto.getAddressId(),
                orderDto.getProductIds().toString(),
                orderDto.getProductQuantities().toString(),
                orderDto.getPaymentType(),
                orderDto.getPaymentStatus(),
                orderDto.getOrderDate(),
                orderDto.getDeliveryDate(),
                orderDto.getShippingStatus()
        );
    }

    public static OrderFinalDto toOrderFinalDto(Order order,
                                                ICustomerService ICustomerService,
                                                IAddressService IAddressService)
    {
        return new OrderFinalDto(
                order.getOrderId(),
                ICustomerService.getCustomerById(order.getCustomerId()),
                IAddressService.getAddressById(order.getAddressId()),
                ProductMapper.mapProductQuantitiesWithProductFinalDtoList(
                        order.getProductIds(),
                        order.getProductQuantities()),
                order.getPaymentType(),
                order.getPaymentStatus(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getShippingStatus()
        );
    }
}
