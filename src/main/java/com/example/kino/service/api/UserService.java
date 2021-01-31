package com.example.kino.service.api;

import com.example.kino.config.dto.CreateUserRequest;
import com.example.kino.config.dto.UserDto;

/**
 * Interface for login and register users
 */
public interface UserService {
    UserDto findByUsername(String username);

    UserDto createUser(CreateUserRequest request);
}
