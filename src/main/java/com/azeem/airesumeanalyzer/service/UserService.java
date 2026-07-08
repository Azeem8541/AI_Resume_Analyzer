package com.azeem.airesumeanalyzer.service;

import com.azeem.airesumeanalyzer.dto.LoginRequestDTO;
import com.azeem.airesumeanalyzer.dto.LoginResponseDTO;
import com.azeem.airesumeanalyzer.dto.RegisterRequestDTO;
import com.azeem.airesumeanalyzer.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(RegisterRequestDTO request);

    LoginResponseDTO loginUser(LoginRequestDTO request);

}