package com.example.kino.service.impl;

import com.example.kino.config.dto.CreateUserRequest;
import com.example.kino.config.dto.UserDto;
import com.example.kino.entity.user.User;
import com.example.kino.entity.user.UserRole;
import com.example.kino.exeption.ResourceAlreadyExistsException;
import com.example.kino.exeption.ResourceNotFoundException;
import com.example.kino.repo.UserRepository;
import com.example.kino.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findByUsername(String username) {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() ->
                new ResourceNotFoundException("User doesn't exists"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (!existUserByUsername(request.getUsername())) {
            User newUser = modelMapper.map(request, User.class);
            newUser.setRole(UserRole.USER);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(newUser);
            return modelMapper.map(newUser, UserDto.class);
        } else throw new ResourceAlreadyExistsException("User exist with username!");
    }

    private Boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
