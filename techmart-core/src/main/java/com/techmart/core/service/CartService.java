package com.techmart.core.service;

import com.techmart.core.dto.request.CartItemRequestDto;
import com.techmart.core.dto.response.CartItemResponseDto;
import jakarta.ejb.Remote;
import jakarta.ejb.Remove;

import java.math.BigDecimal;
import java.util.List;

@Remote
public interface CartService {
    void addItem(CartItemRequestDto requestDto);
    void removeItem(Integer productId);
    void updateQuantity(Integer productId, Integer quantity);
    List<CartItemResponseDto> getCartItems();
    BigDecimal getTotalAmount();

    @Remove
    void clearCart();
    int getCartSize();
}