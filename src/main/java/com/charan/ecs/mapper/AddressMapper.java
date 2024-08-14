package com.charan.ecs.mapper;

import com.charan.ecs.dto.AddressDto;
import com.charan.ecs.entity.Address;

public class AddressMapper {

    public static Address mapToAddress(AddressDto addressDto) {
        return new Address(
                addressDto.getAddressId(),
                addressDto.getCustomerId(),
                addressDto.getStreet(),
                addressDto.getCity(),
                addressDto.getState(),
                addressDto.getZip(),
                addressDto.getCountry()
        );
    }

    public static AddressDto mapToAddressDto(Address address) {
        return new AddressDto(
                address.getAddressId(),
                address.getCustomerId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getCountry()
        );
    }
}
