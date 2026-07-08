package com.azeem.airesumeanalyzer.controller;

import com.azeem.airesumeanalyzer.dto.LoginRequestDTO;
import com.azeem.airesumeanalyzer.dto.LoginResponseDTO;
import com.azeem.airesumeanalyzer.dto.RegisterRequestDTO;
import com.azeem.airesumeanalyzer.dto.UserResponseDTO;
import com.azeem.airesumeanalyzer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody RegisterRequestDTO request) {

        UserResponseDTO response = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @RequestBody LoginRequestDTO loginRequestDTO) {

        return ResponseEntity.ok(userService.loginUser(loginRequestDTO));
    }
}