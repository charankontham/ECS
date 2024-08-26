package com.charan.ecs.service.interfaces;

import com.charan.ecs.dto.UserDto;

public interface IUserService {

    UserDto getUserById(int userId);

    UserDto getUserByUsername(String username);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    boolean deleteUserById(int userId);
}
