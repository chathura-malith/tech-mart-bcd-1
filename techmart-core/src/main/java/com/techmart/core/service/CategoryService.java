package com.techmart.core.service;

import com.techmart.core.dto.request.CategoryRequestDto;
import com.techmart.core.dto.response.CategoryResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface CategoryService {
    void addCategory(CategoryRequestDto requestDto);
    List<CategoryResponseDto> getAllCategories();
}