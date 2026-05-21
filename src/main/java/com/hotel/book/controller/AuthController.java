package com.hotel.book.controller;

import com.hotel.book.model.User;
import com.hotel.book.repository.UserRepository;
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
    public String signUp(@RequestBody User user) {
        logger.info("Signup request received for user: {}", user.name());

        if (userRepository.existsByName(user.name())) {
            return "User already exists";
        }

        userRepository.save(user);
        return "User registered successfully";
    }
}
