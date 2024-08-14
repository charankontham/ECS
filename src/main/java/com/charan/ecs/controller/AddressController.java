package com.charan.ecs.controller;

import com.charan.ecs.dto.AddressDto;
import com.charan.ecs.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable("id") int addressId){
        AddressDto addressDto = addressService.getAddressById(addressId);
        return ResponseEntity.ok(addressDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressDto addressDto){
        Object response = addressService.addAddress(addressDto);
        if(response == HttpStatus.CONFLICT){
            return new ResponseEntity<>("Address already exists with ID: "+addressDto.getAddressId(), HttpStatus.CONFLICT);
        }
        if(response == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>("Customer Mapping Failed!", HttpStatus.NOT_FOUND);
        }
        if(Objects.nonNull(response)){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Validation Failed!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(@RequestBody AddressDto addressDto){
        Object response = addressService.updateAddress(addressDto);
        if(response == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>("Address Not Found!", HttpStatus.NOT_FOUND);
        }else if (response == HttpStatus.NOT_ACCEPTABLE){
            return new ResponseEntity<>("Customer Mapping Failed!", HttpStatus.BAD_REQUEST);
        }
        if(Objects.nonNull(response)){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Validation Failed!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable("id") int addressId){
        boolean isDeleted = addressService.deleteAddressById(addressId);
        if(isDeleted){
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Address not found!", HttpStatus.NOT_FOUND);
    }
}
