package com.hotel.book.controller;

import com.hotel.book.model.User;
import com.hotel.book.repository.UserRepository;
import com.hotel.book.responseDTO.RegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public RegisterResponse signUp(@RequestBody User user) {
        logger.info("Signup request received for user: {}", user.name());

        if (userRepository.existsByNameAndPassword(user.name(), user.password())) {
            return new RegisterResponse("User already exists");
        }

        userRepository.save(user);
        return new RegisterResponse("User registered successfully");
    }
}
