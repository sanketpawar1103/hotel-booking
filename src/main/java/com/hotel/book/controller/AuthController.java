package com.hotel.book.controller;

import com.hotel.book.model.User;
import com.hotel.book.mongodbServiceImp.UserServiceImpl;
import com.hotel.book.repository.UserRepository;
import com.hotel.book.requestDTO.LoginRequest;
import com.hotel.book.requestDTO.RegisterRequest;
import com.hotel.book.responseDTO.LoginResponse;
import com.hotel.book.responseDTO.RegisterResponse;
import com.hotel.book.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegisterResponse signUp(@RequestBody RegisterRequest user) {
        logger.info("Signup request received for user: {}", user.name());

        return userService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest user) {
        logger.info("Login request comes");
        return userService.login(user);
    }
}
