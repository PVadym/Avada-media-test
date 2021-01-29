package com.example.kino.config.dto;

import com.example.kino.entity.user.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private UserRole role;
}
