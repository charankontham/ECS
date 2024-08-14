package com.charan.ecs.controller;

import com.charan.ecs.dto.OrderDto;
import com.charan.ecs.dto.OrderFinalDto;
import com.charan.ecs.service.interfaces.OrderServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderServiceInterface orderServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<OrderFinalDto> getOrderById(@PathVariable("id") int orderId){
        OrderFinalDto orderFinalDto = orderServiceInterface.getOrderById(orderId);
        return ResponseEntity.ok(orderFinalDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderFinalDto>> getAllOrders(){
        List<OrderFinalDto> orders = orderServiceInterface.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getOrdersByCustomerId/{id}")
    public ResponseEntity<List<OrderFinalDto>> getAllOrdersByCustomerId(@PathVariable("id") int customerId){
        List<OrderFinalDto> orders = orderServiceInterface.getAllOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto){
        Object response = orderServiceInterface.addOrder(orderDto);
        if(Objects.equals(response, HttpStatus.CONFLICT)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Order already exists with Id : " + orderDto.getOrderId());
        }
        return HelperFunctions.getResponseEntity(response);
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto){
        Object response = orderServiceInterface.updateOrder(orderDto);
        if(Objects.equals(response, Constants.OrderNotFound)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Not Found with Id : " + orderDto.getOrderId());
        }
        return HelperFunctions.getResponseEntity(response);
    }

}
