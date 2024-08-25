package com.charan.ecs.controller;

import com.charan.ecs.dto.CartDto;
import com.charan.ecs.service.interfaces.CartServiceInterface;
import com.charan.ecs.util.Constants;
import com.charan.ecs.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServiceInterface cartServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable("id") int cartId) {
        return ResponseEntity.ok(cartServiceInterface.getCart(cartId));
    }

    @GetMapping("/getCartByCustomerId/{id}")
    public ResponseEntity<?> getCartByCustomerId(@PathVariable("id") int customerId) {
        return ResponseEntity.ok(cartServiceInterface.getCartByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody CartDto cartDto) {
        Object response = cartServiceInterface.addCart(cartDto);
        if (Objects.equals(response, HttpStatus.CONFLICT)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cart already exists with Id : " + cartDto.getCartId());
        }
        ResponseEntity<?> responseEntity = HelperFunctions.getResponseEntity(response);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity.getBody());
        }
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<?> updateCart(@RequestBody CartDto cartDto) {
        Object response = cartServiceInterface.updateCart(cartDto);
        if(Objects.equals(response, Constants.CartNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found!");
        }
        return HelperFunctions.getResponseEntity(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") int cartId) {
        boolean response = cartServiceInterface.deleteCart(cartId);
        if(response){
            return ResponseEntity.status(HttpStatus.OK).body("Cart successfully deleted!");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found!");
        }
    }
}
