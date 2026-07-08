package com.azeem.airesumeanalyzer.service.impl;

import com.azeem.airesumeanalyzer.dto.LoginRequestDTO;
import com.azeem.airesumeanalyzer.dto.LoginResponseDTO;
import com.azeem.airesumeanalyzer.dto.RegisterRequestDTO;
import com.azeem.airesumeanalyzer.dto.UserResponseDTO;
import com.azeem.airesumeanalyzer.entity.User;
import com.azeem.airesumeanalyzer.exception.EmailAlreadyExistsException;
import com.azeem.airesumeanalyzer.repository.UserRepository;
import com.azeem.airesumeanalyzer.security.JwtUtil;
import com.azeem.airesumeanalyzer.service.UserService;
import com.azeem.airesumeanalyzer.util.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseDTO registerUser(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponseDTO(savedUser);
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid Email");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                token
        );
    }
}