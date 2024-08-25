package com.charan.ecs.controller;

import com.charan.ecs.dto.CustomerDto;
import com.charan.ecs.service.interfaces.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceInterface customerServiceInterface;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto) {
        if(customerServiceInterface.isEmailExist(customerDto.getEmail())){
            return new ResponseEntity<>( "Duplicate email", HttpStatus.CONFLICT);
        }
        CustomerDto newCustomer = customerServiceInterface.createCustomer(customerDto);
        if(Objects.nonNull(newCustomer)){
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Validation failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtoList = customerServiceInterface.getAllCustomers();
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") int customerId) {
        CustomerDto customer = customerServiceInterface.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @RequestMapping(value = "/getByEmail", method = RequestMethod.GET, params="email")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@RequestParam("email") String email) {
        System.out.println("email: " + email);
        CustomerDto customerDto = customerServiceInterface.getCustomerByEmail(email);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping()
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto) {
        if(customerServiceInterface.updatingDuplicateEmail(customerDto)){
            return new ResponseEntity<>("Duplicate Email", HttpStatus.CONFLICT);
        }
        CustomerDto updatedCustomer = customerServiceInterface.updateCustomer(customerDto);
        if(Objects.nonNull(updatedCustomer)){
            return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Validation failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int customerId) {
//        List<AddressDto> addressList = addressServiceInterface.getAllAddressByCustomerId(customerId);
//        CartFinalDto cartFinalDto = cartServiceInterface.getCartByCustomerId(customerId);
        customerServiceInterface.deleteCustomerById(customerId);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> customerLogin(@RequestParam String email, @RequestParam String password) {
        Object response = customerServiceInterface.customerLogin(email, password);
        if(response == HttpStatus.UNAUTHORIZED){
            return new ResponseEntity<>("Wrong Password", HttpStatus.UNAUTHORIZED);
        }else if (response == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
