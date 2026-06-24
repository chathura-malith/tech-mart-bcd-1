package com.techmart.ejb;

import com.techmart.core.dto.request.CartItemRequestDto;
import com.techmart.core.dto.response.CartItemResponseDto;
import com.techmart.core.dto.response.ProductResponseDto;
import com.techmart.core.service.CartService;
import com.techmart.core.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class CartServiceBean implements CartService {

    @EJB
    private ProductService productService;

    private final Map<Integer, CartItemResponseDto> cart = new LinkedHashMap<>();

    @Override
    public void addItem(CartItemRequestDto requestDto) {
        Integer productId = requestDto.getProductId();
        Integer quantity = requestDto.getQuantity();

        if (cart.containsKey(productId)) {
            CartItemResponseDto existingItem = cart.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            ProductResponseDto product = productService.getProductById(productId);

            if (product != null) {
                CartItemResponseDto newItem = CartItemResponseDto.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .price(product.getPrice())
                        .imageUrl(product.getImageUrl())
                        .quantity(quantity)
                        .build();

                cart.put(productId, newItem);
            }
        }
    }

    @Override
    public void removeItem(Integer productId) {
        cart.remove(productId);
    }

    @Override
    public void updateQuantity(Integer productId, Integer quantity) {
        if (cart.containsKey(productId)) {
            if (quantity <= 0) {
                cart.remove(productId);
            } else {
                cart.get(productId).setQuantity(quantity);
            }
        }
    }

    @Override
    public List<CartItemResponseDto> getCartItems() {
        return new ArrayList<>(cart.values());
    }

    @Override
    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemResponseDto item : cart.values()) {
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }

    @Override
    public void clearCart() {
        cart.clear();
    }

    @Override
    public int getCartSize() {
        int totalQuantity = 0;
        for (CartItemResponseDto item : cart.values()) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;

    }
}