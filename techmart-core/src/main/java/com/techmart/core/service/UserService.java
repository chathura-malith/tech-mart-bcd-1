package com.techmart.core.service;

import com.techmart.core.dto.request.UserRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UserService {
    void registerUser(UserRequestDto requestDto);
    UserResponseDto getUserById(Integer id);
    UserResponseDto getUserByEmail(String email);
    List<UserResponseDto> getAllUsers();
    void updateUser(Integer id, UserRequestDto requestDto);
    void deleteUser(Integer id);
}