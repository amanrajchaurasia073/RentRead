package com.aman.service;

import com.aman.dto.LoginRequest;
import com.aman.dto.RegisterRequest;
import com.aman.exception.ResourceNotFoundException;
import com.aman.exception.UnauthorizedException;
import com.aman.model.User;
import com.aman.repository.UserRepository;
import com.aman.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "USER");
        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Generate and return a JWT token
            return jwtUtil.generateToken(user.getEmail());
        } else {
            throw new UnauthorizedException("Invalid Credentials !");
        }
    }
}
