package com.charan.ecs.validations;

import com.charan.ecs.dto.CustomerDto;

public class CustomerValidation {
    public static boolean validateCustomer(CustomerDto customerDto) {
        return BasicValidation.stringValidation(customerDto.getCustomerName()) ||
                BasicValidation.stringValidation(customerDto.getEmail()) ||
                BasicValidation.stringValidation(customerDto.getPassword());
    }
}
