package com.hotel.book.mongodbServiceImp;

import com.hotel.book.model.User;
import com.hotel.book.repository.UserRepository;
import com.hotel.book.requestDTO.LoginRequest;
import com.hotel.book.requestDTO.RegisterRequest;
import com.hotel.book.responseDTO.LoginResponse;
import com.hotel.book.responseDTO.RegisterResponse;
import com.hotel.book.security.JwtService;
import com.hotel.book.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterResponse register(RegisterRequest user) {
        if (userRepository.existsByName(user.name()) && userRepository.existsByPassword(user.password())) {
            return new RegisterResponse("User already exists");
        }

        User newUser = new User(null, user.name(), user.password());
        userRepository.save(newUser);

        return new RegisterResponse("User registered successfully");
    }

    @Override
    public LoginResponse login(LoginRequest user) {
        User existingUser = userRepository.findByName(user.name());

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!existingUser.password().equals(user.password())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(existingUser.id());

        return new LoginResponse(token);
    }
}
