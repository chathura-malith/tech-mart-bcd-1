package com.techmart.core.service;

import com.techmart.core.dto.request.LoginRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import jakarta.ejb.Remote;

@Remote
public interface AuthService {
    UserResponseDto login(LoginRequestDto loginDto);
}