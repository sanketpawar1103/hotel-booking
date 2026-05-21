package com.hotel.book.service;

import com.hotel.book.requestDTO.LoginRequest;
import com.hotel.book.requestDTO.RegisterRequest;
import com.hotel.book.responseDTO.LoginResponse;
import com.hotel.book.responseDTO.RegisterResponse;

public interface UserService {
    public RegisterResponse register(RegisterRequest user);
    public LoginResponse login(LoginRequest user);
}
