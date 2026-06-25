package com.techmart.core.service;

import com.techmart.core.dto.request.AddressRequestDto;
import com.techmart.core.dto.response.AddressResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface AddressService {
    List<AddressResponseDto> getUserAddresses(Integer userId);
    Integer addAddress(Integer userId, AddressRequestDto requestDto);
}