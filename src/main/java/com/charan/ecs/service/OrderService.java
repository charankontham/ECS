package com.charan.ecs.service;

import com.charan.ecs.dto.CustomerDto;
import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import com.charan.ecs.dto.ProductFinalDto;
import com.charan.ecs.entity.Order;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.OrderMapper;
import com.charan.ecs.repository.OrderRepository;
import com.charan.ecs.service.interfaces.AddressServiceInterface;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import com.charan.ecs.service.interfaces.OrderServiceInterface;
import com.charan.ecs.service.interfaces.ProductServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import com.charan.ecs.validations.OrderValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {
    private OrderRepository orderRepository;
    private final CustomerServiceInterface customerServiceInterface;
    private final ProductServiceInterface productServiceInterface;
    private final AddressServiceInterface addressServiceInterface;

    @Override
    public OrderFinalDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        return OrderMapper.toOrderFinalDto(
                order,
                productServiceInterface,
                customerServiceInterface,
                addressServiceInterface);
    }

    @Override
    public List<OrderFinalDto> getAllOrdersByCustomerId(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream().map((order) -> OrderMapper.toOrderFinalDto(
                order,
                productServiceInterface,
                customerServiceInterface,
                addressServiceInterface)).collect(Collectors.toList());
    }

    @Override
    public List<OrderFinalDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((order) -> OrderMapper.toOrderFinalDto(
                order,
                productServiceInterface,
                customerServiceInterface,
                addressServiceInterface)).collect(Collectors.toList()
        );
    }

    @Override
    public Object addOrder(OrderDto orderDto) {
        boolean orderExists = orderRepository.existsById(orderDto.getOrderId());
        if (orderExists) {
            return HttpStatus.CONFLICT;
        }
        return validateAndSaveOrder(orderDto);
    }

    @Override
    public Object updateOrder(OrderDto orderDto) {
        boolean orderExists = orderRepository.existsById(orderDto.getOrderId());
        if (orderExists) {
            Order order = orderRepository.findById(orderDto.getOrderId()).orElseThrow();
            OrderDto existingOrderDto = OrderMapper.toOrderDto(order);
            orderDto.setCustomerId(existingOrderDto.getCustomerId());
            orderDto.setProductIds(existingOrderDto.getProductIds());
            orderDto.setProductQuantities(existingOrderDto.getProductQuantities());
            return validateAndSaveOrder(orderDto);
        }
        return Constants.OrderNotFound;
    }

    @Override
    public Object validateAndSaveOrder(OrderDto orderDto) {
        if (!OrderValidation.validateOrderRequestSchema(orderDto)) {
            return HttpStatus.BAD_REQUEST;
        }
        Object response = HelperFunctions.
                validateCustomerProductAndProductQuantities(
                        orderDto.getCustomerId(),
                        orderDto.getProductIds(),
                        orderDto.getProductQuantities(),
                        productServiceInterface,
                        customerServiceInterface
                );
        if (Objects.equals(response, Constants.NoErrorFound)) {
            response = HelperFunctions.validateAddress(orderDto.getAddressId(), orderDto.getCustomerId(), addressServiceInterface);
            if (Objects.equals(response, Constants.NoErrorFound)) {
                Order savedOrder = orderRepository.save(OrderMapper.toOrder(orderDto));
                return OrderMapper.toOrderFinalDto(
                        savedOrder,
                        productServiceInterface,
                        customerServiceInterface,
                        addressServiceInterface);
            }
        }
        return response;
    }
}
