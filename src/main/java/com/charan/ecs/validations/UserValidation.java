package com.charan.ecs.validations;

import com.charan.ecs.dto.UserDto;

import java.util.Objects;

public class UserValidation {

    public static boolean validateUser(UserDto userDto){
        return BasicValidation.stringValidation(userDto.getUsername())
                && BasicValidation.stringValidation(userDto.getPassword())
                && Objects.nonNull(userDto.getRole()) ;
    }
}
