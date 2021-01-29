package com.example.kino.config.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
}
