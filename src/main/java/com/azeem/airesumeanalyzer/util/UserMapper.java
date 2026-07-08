package com.azeem.airesumeanalyzer.util;

import com.azeem.airesumeanalyzer.dto.RegisterRequestDTO;
import com.azeem.airesumeanalyzer.dto.UserResponseDTO;
import com.azeem.airesumeanalyzer.entity.User;

public class UserMapper {

    // RegisterRequestDTO -> User Entity
    public static User toEntity(RegisterRequestDTO dto) {

        User user = new User();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    // User Entity -> UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());

        return dto;
    }

}