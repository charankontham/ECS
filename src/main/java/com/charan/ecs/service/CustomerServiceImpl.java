package com.charan.ecs.service;

import com.charan.ecs.dto.CustomerDto;
import com.charan.ecs.entity.Customer;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.CustomerMapper;
import com.charan.ecs.repository.CustomerRepository;
import com.charan.ecs.service.interfaces.ICustomerService;
import com.charan.ecs.validations.CustomerValidation;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (
                CustomerValidation.validateCustomer(customerDto) &&
                        customerRepository.findByEmail(customerDto.getEmail()).isEmpty()
        ) {
            String encryptedPassword = Hashing.sha256().
                    hashString(customerDto.getPassword(), StandardCharsets.UTF_8).
                    toString();
            customerDto.setPassword(encryptedPassword);
            Customer customerEntity = CustomerMapper.mapToCustomer(customerDto);
            Customer savedCustomer = customerRepository.save(customerEntity);
            return CustomerMapper.mapToCustomerDto(savedCustomer);
        }
        return null;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map((CustomerMapper::mapToCustomerDto))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(int customerId) {
        Customer retrievedCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer not exists"));
        return CustomerMapper.mapToCustomerDto(retrievedCustomer);
    }

    @Override
    public CustomerDto getCustomerByEmail(String email) {
        Customer retrievedCustomer = customerRepository.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("Customer not exists"));
        System.out.println(retrievedCustomer.getCustomerName());
        return CustomerMapper.mapToCustomerDto(retrievedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        if (CustomerValidation.validateCustomer(customerDto)) {
            String encryptedPassword = Hashing.sha256().
                    hashString(customerDto.getPassword(), StandardCharsets.UTF_8).toString();
            customerDto.setPassword(encryptedPassword);
            Customer updatedCustomer = customerRepository.save(CustomerMapper.mapToCustomer(customerDto));
            return CustomerMapper.mapToCustomerDto(updatedCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomerById(int customerId) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
        customerRepository.deleteById(customer.getCustomerId());
    }

    @Override
    public Object customerLogin(String email, String password) {
        Customer customerEntity = customerRepository.findByEmail(email).orElse(null);
        if (Objects.isNull(customerEntity)) {
            return HttpStatus.NOT_FOUND;
        } else {
            String encryptedPassword = Hashing.sha256().
                    hashString(password, StandardCharsets.UTF_8).
                    toString();
            if (Objects.nonNull(customerEntity.getPassword()) && customerEntity.getPassword().equals(encryptedPassword))
                return CustomerMapper.mapToCustomerDto(customerEntity);
            else
                return HttpStatus.UNAUTHORIZED;
        }
    }

    @Override
    public boolean updatingDuplicateEmail(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.findById(customerDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        if (customerDto.getEmail().equals(customerEntity.getEmail())) {
            return false;
        } else {
            return isEmailExist(customerDto.getEmail());
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isCustomerExist(int customerId) {
        return customerRepository.existsById(customerId);
    }
}
