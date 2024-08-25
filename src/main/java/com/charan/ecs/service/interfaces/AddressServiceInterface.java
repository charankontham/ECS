package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.AddressDto;

import java.util.List;

public interface AddressServiceInterface {

    AddressDto getAddressById(int addressId);

    List<AddressDto> getAllAddressByCustomerId(int customerId);

    List<AddressDto> getAllAddresses();

    Object addAddress(AddressDto addressDto);

    Object updateAddress(AddressDto addressDto);

    boolean deleteAddressById(int addressId);

    void deleteAllAddressByIds(List<Integer> addressIds);

    boolean isAddressExists(int addressId);
}
