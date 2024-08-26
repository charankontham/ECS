package com.charan.ecs.service;

import com.charan.ecs.dto.UserDto;
import com.charan.ecs.entity.User;
import com.charan.ecs.exception.ResourceNotFoundException;
import com.charan.ecs.mapper.UserMapper;
import com.charan.ecs.repository.UserRepository;
import com.charan.ecs.service.interfaces.IUserService;
import com.charan.ecs.validations.UserValidation;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if(
                UserValidation.validateUser(userDto)
                        && !userRepository.existsById(userDto.getUserId())
                        && userRepository.findByUsername(userDto.getUsername()).
                        isEmpty()
        ){
            String encryptedPassword = Hashing.sha256().
                    hashString(userDto.getPassword(), StandardCharsets.UTF_8).toString();
            userDto.setPassword(encryptedPassword);
            User newUser = userRepository.save(UserMapper.mapToUser(userDto));
            return UserMapper.mapToUserDto(newUser);
        }
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        boolean userIdExists = userRepository.existsById(userDto.getUserId());
        boolean userUsernameExists = userRepository.findByUsername(userDto.getUsername()).isPresent();
        if(UserValidation.validateUser(userDto) && userIdExists && userUsernameExists ){
            String encryptedPassword = Hashing.sha256().
                    hashString(userDto.getPassword(), StandardCharsets.UTF_8).toString();
            userDto.setPassword(encryptedPassword);
            User updatedUser = userRepository.save(UserMapper.mapToUser(userDto));
            return UserMapper.mapToUserDto(updatedUser);
        }
        return null;
    }

    @Override
    public boolean deleteUserById(int userId) {
        if(userId !=0 && userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
