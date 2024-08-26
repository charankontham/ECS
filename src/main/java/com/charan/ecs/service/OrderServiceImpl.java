package com.charan.ecs.service;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import com.charan.ecs.entity.Order;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.OrderMapper;
import com.charan.ecs.repository.OrderRepository;
import com.charan.ecs.service.interfaces.IAddressService;
import com.charan.ecs.service.interfaces.ICustomerService;
import com.charan.ecs.service.interfaces.IOrderService;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import com.charan.ecs.validations.OrderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IAddressService addressService;

    @Override
    public OrderFinalDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        return OrderMapper.toOrderFinalDto(order, customerService, addressService);
    }

    @Override
    public List<OrderFinalDto> getAllOrdersByCustomerId(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream().
                map(
                        (order) -> OrderMapper.toOrderFinalDto(
                                order,
                                customerService,
                                addressService
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByProductId(int productId) {
        List<Order> orders = orderRepository.findAllByProductId(productId);
        return orders.stream().map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderFinalDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().
                map(
                        (order) -> OrderMapper.toOrderFinalDto(
                                order,
                                customerService,
                                addressService
                        )
                ).collect(Collectors.toList()
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
        return this.updateOrder(orderDto, false);
    }

    @Override
    public Object updateOrder(OrderDto orderDto, boolean forceUpdate) {
        boolean orderExists = orderRepository.existsById(orderDto.getOrderId());
        if (orderExists) {
            Order order = orderRepository.findById(orderDto.getOrderId()).
                    orElseThrow(() -> new ResourceNotFoundException("Order Not Found!"));
            OrderDto existingOrderDto = OrderMapper.toOrderDto(order);
            orderDto.setCustomerId(existingOrderDto.getCustomerId());
            if (!forceUpdate) {
                orderDto.setProductIds(existingOrderDto.getProductIds());
                orderDto.setProductQuantities(existingOrderDto.getProductQuantities());
            }
            return validateAndSaveOrder(orderDto);
        }
        return Constants.OrderNotFound;
    }

    private Object validateAndSaveOrder(OrderDto orderDto) {
        if (!OrderValidation.validateOrderRequestSchema(orderDto)) {
            return HttpStatus.BAD_REQUEST;
        }
        Object response = HelperFunctions.
                validateCustomerProductAndProductQuantities(
                        orderDto.getCustomerId(),
                        orderDto.getProductIds(),
                        orderDto.getProductQuantities()
                );
        if (Objects.equals(response, Constants.NoErrorFound)) {
            response = HelperFunctions.validateAddress(orderDto.getAddressId(), orderDto.getCustomerId());
            if (Objects.equals(response, Constants.NoErrorFound)) {
                Order savedOrder = orderRepository.save(OrderMapper.toOrder(orderDto));
                return OrderMapper.
                        toOrderFinalDto(
                                savedOrder,
                                customerService,
                                addressService
                        );
            }
        }
        return response;
    }

    @Override
    public void deleteOrderById(int orderId) {
        orderRepository.deleteById(orderId);
    }
}
