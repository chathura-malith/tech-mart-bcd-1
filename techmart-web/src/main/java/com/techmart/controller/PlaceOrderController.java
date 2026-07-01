package com.techmart.controller;

import com.techmart.core.dto.message.OrderItemMessageDto;
import com.techmart.core.dto.message.OrderMessageDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.service.CartService;
import com.techmart.core.service.OrderDispatcherService;
import com.techmart.core.service.PaymentAndNotificationService;
import com.techmart.core.service.SystemMetricsService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@WebServlet(name = "PlaceOrderController", urlPatterns = {"/place-order"})
public class PlaceOrderController extends HttpServlet {


    @EJB
    private OrderDispatcherService orderDispatcherService;

    @EJB
    private PaymentAndNotificationService paymentService;

    @EJB
    private SystemMetricsService metricsService;

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

        String cardNumber = request.getParameter("cardNumber");
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/checkout?error=invalid_card");
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

            Future<Boolean> paymentResult = paymentService.processPayment(cardNumber, cartService.getTotalAmount());

            try {
                Boolean isPaid = paymentResult.get(3, TimeUnit.SECONDS);

                if (isPaid != null && isPaid) {

                    orderDispatcherService.dispatchOrder(orderMessage);
                    paymentService.sendConfirmationEmail(user.getEmail());

                    if (metricsService != null) {
                        int totalItemsInOrder = cartService.getCartItems().stream()
                                .mapToInt(item -> item.getQuantity())
                                .sum();
                        metricsService.recordSale(totalItemsInOrder);
                    }

                    cartService.clearCart();
                    session.removeAttribute("cartService");

                    response.sendRedirect(request.getContextPath() + "/order-success.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/checkout?error=payment_failed");
                }

            } catch (TimeoutException e) {
                System.err.println(" [!] Payment gateway timed out!");
                paymentResult.cancel(true);
                response.sendRedirect(request.getContextPath() + "/checkout?error=timeout");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/checkout?error=payment_error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/checkout?error=server_error");
        }
    }
}