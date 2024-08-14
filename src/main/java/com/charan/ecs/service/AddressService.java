package com.charan.ecs.service;

import com.charan.ecs.dto.AddressDto;
import com.charan.ecs.entity.Address;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.AddressMapper;
import com.charan.ecs.repository.AddressRepository;
import com.charan.ecs.repository.CustomerRepository;
import com.charan.ecs.service.interfaces.AddressServiceInterface;
import com.charan.ecs.validations.AddressValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService implements AddressServiceInterface {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Override
    public AddressDto getAddressById(int addressId) {
        Address address = addressRepository.findById(addressId).
                orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        return AddressMapper.mapToAddressDto(address);
    }

    @Override
    public List<AddressDto> getAllAddressByCustomerId(int customerId) {
        List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public Object addAddress(AddressDto addressDto) {
        boolean customerExists = customerRepository.existsById(addressDto.getCustomerId());
        if(!customerExists) {
            return HttpStatus.NOT_FOUND;
        }
        boolean addressExists = addressRepository.existsById(addressDto.getAddressId());
        if(addressExists) {
            return HttpStatus.CONFLICT;
        }
        if(AddressValidation.validateAddress(addressDto)) {
            Address address = addressRepository.save(AddressMapper.mapToAddress(addressDto));
            return AddressMapper.mapToAddressDto(address);
        }
        return null;
    }

    @Override
    public Object updateAddress(AddressDto addressDto) {
        boolean customerExists = customerRepository.existsById(addressDto.getCustomerId());
        if(!customerExists){
            return HttpStatus.NOT_ACCEPTABLE;
        }
        boolean addressExists = addressRepository.existsById(addressDto.getAddressId());
        if(!addressExists) {
            return HttpStatus.NOT_FOUND;
        }
        if(AddressValidation.validateAddress(addressDto)){
            Address address = addressRepository.save(AddressMapper.mapToAddress(addressDto));
            return AddressMapper.mapToAddressDto(address);
        }
        return null;
    }

    @Override
    public boolean deleteAddressById(int addressId) {
        if(addressId!=0 && addressRepository.existsById(addressId)){
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllAddressByIds(List<Integer> addressIds) {
        for(Integer addressId : addressIds) {
            if(addressId!=0 && addressRepository.existsById(addressId)) {
                addressRepository.deleteById(addressId);
            }
        }
    }

    @Override
    public boolean addressExists(int addressId) {
        return addressRepository.existsById(addressId);
    }
}
