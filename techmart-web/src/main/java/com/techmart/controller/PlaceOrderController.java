package com.techmart.controller;

import com.techmart.core.dto.message.OrderItemMessageDto;
import com.techmart.core.dto.message.OrderMessageDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.service.CartService;
import com.techmart.core.service.OrderDispatcherService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PlaceOrderController", urlPatterns = {"/place-order"})
public class PlaceOrderController extends HttpServlet {


    @EJB
    private OrderDispatcherService orderDispatcherService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        CartService cartService = (CartService) session.getAttribute("cartService");

        if (cartService == null || cartService.getCartSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        String addressIdStr = request.getParameter("selectedAddressId");
        if (addressIdStr == null || addressIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/checkout?error=address_missing");
            return;
        }

        try {
            Integer addressId = Integer.valueOf(addressIdStr);

            List<OrderItemMessageDto> orderItems = cartService.getCartItems().stream()
                    .map(item -> OrderItemMessageDto.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getPrice())
                            .build())
                    .collect(Collectors.toList());

            OrderMessageDto orderMessage = OrderMessageDto.builder()
                    .userId(user.getId())
                    .addressId(addressId)
                    .totalAmount(cartService.getTotalAmount())
                    .items(orderItems)
                    .build();

            orderDispatcherService.dispatchOrder(orderMessage);

            cartService.clearCart();

            session.removeAttribute("cartService");

            response.sendRedirect(request.getContextPath() + "/order-success.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/checkout?error=server_error");
        }
    }
}