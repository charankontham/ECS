package com.charan.ecs.validations;

import com.charan.ecs.dto.AddressDto;

public class AddressValidation {
    public static boolean validateAddress(AddressDto addressDto) {
        return BasicValidation.stringValidation(addressDto.getStreet())
                && BasicValidation.stringValidation(addressDto.getCity())
                && BasicValidation.stringValidation(addressDto.getState())
                && BasicValidation.stringValidation(addressDto.getZip())
                && BasicValidation.stringValidation(addressDto.getCountry());
    }
}
