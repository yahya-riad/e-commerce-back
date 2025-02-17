package com.alten.ecommerce.service;

import com.alten.ecommerce.model.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUserByEmail(String email);
    void createUser(UserDto userDto);
}
