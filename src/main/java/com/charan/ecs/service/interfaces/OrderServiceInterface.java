package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import java.util.List;

public interface OrderServiceInterface {

    OrderFinalDto getOrderById(int orderId);

    List<OrderFinalDto> getAllOrdersByCustomerId(int customerId);

    List<OrderFinalDto> getAllOrders();

    Object addOrder(OrderDto orderDto);

    Object updateOrder(OrderDto orderDto);

    Object validateAndSaveOrder(OrderDto orderDto);

}
