package com.techmart.controller;

import com.techmart.core.dto.response.AddressResponseDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.service.AddressService;
import com.techmart.core.service.CartService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @EJB
    private AddressService addressService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        CartService cartService = (CartService) session.getAttribute("cartService");
        if (cartService == null || cartService.getCartSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        try {
            List<AddressResponseDto> addresses = addressService.getUserAddresses(user.getId());

            request.setAttribute("addresses", addresses);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading checkout page");
        }
    }
}