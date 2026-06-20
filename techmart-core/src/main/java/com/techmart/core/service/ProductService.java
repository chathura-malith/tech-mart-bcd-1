package com.techmart.core.service;

import com.techmart.core.dto.request.ProductRequestDto;
import com.techmart.core.dto.response.ProductResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProductService {
    void addProduct(ProductRequestDto requestDto);
    List<ProductResponseDto> getAllProducts();
}