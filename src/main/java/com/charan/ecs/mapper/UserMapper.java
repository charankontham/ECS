package com.charan.ecs.mapper;

import com.charan.ecs.dto.UserDto;
import com.charan.ecs.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
