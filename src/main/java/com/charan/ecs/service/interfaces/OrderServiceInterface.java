package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import java.util.List;

public interface OrderServiceInterface {

    OrderFinalDto getOrderById(int orderId);

    List<OrderFinalDto> getAllOrdersByCustomerId(int customerId);

    List<OrderDto> getAllOrdersByProductId(int productId);

    List<OrderFinalDto> getAllOrders();

    Object addOrder(OrderDto orderDto);

    Object updateOrder(OrderDto orderDto);

    Object updateOrder(OrderDto orderDto, boolean forceUpdate);

    void deleteOrderById(int orderId);

}
