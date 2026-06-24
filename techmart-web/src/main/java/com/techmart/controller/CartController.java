//package com.techmart.controller;
//
//import com.techmart.core.dto.request.CartItemRequestDto;
//import com.techmart.core.service.CartService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import java.io.IOException;
//
//@WebServlet(name = "CartController", urlPatterns = {"/add-to-cart"})
//public class CartController extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // බ්‍රව්සර් එකට අපි JSON යවන බව කියන්න ඕනේ
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try {
//            String productIdStr = request.getParameter("productId");
//            String quantityStr = request.getParameter("quantity");
//
//            Integer productId = Integer.parseInt(productIdStr);
//            Integer quantity = (quantityStr != null && !quantityStr.isEmpty())
//                    ? Integer.parseInt(quantityStr) : 1;
//
//            HttpSession session = request.getSession(true);
//            CartService cartService = (CartService) session.getAttribute("cartService");
//
//            if (cartService == null) {
//                InitialContext ic = new InitialContext();
//                // EAR එක ඇතුළේ වෙනම මොඩියුලයක තියෙන Stateful Bean එක හොයාගන්න Global JNDI නම පාවිච්චි කිරීම
//                cartService = (CartService) ic.lookup("java:global/techmart-ear-1.0/ejb/CartServiceBean");
//                session.setAttribute("cartService", cartService);
//            }
//
//            CartItemRequestDto requestDto = CartItemRequestDto.builder()
//                    .productId(productId)
//                    .quantity(quantity)
//                    .build();
//
//            cartService.addItem(requestDto);
//
//            // 🌟 සර්වර් එකෙන් යවන අලුත් JSON Response එක (Cart Size එකත් එක්ක)
//            int newCartSize = cartService.getCartSize();
//            response.getWriter().write("{\"status\":\"success\", \"cartSize\":" + newCartSize + "}");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Error එකක් ආවොත් JSON වලින්ම Error එක යවනවා
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to add item\"}");
//        }
//    }
//}


package com.techmart.controller;

import com.techmart.core.dto.request.CartItemRequestDto;
import com.techmart.core.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import java.io.IOException;

@WebServlet(name = "CartController", urlPatterns = {"/add-to-cart", "/update-cart", "/remove-from-cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getServletPath();

        try {
            HttpSession session = request.getSession(true);
            CartService cartService = (CartService) session.getAttribute("cartService");

            if (cartService == null) {
                InitialContext ic = new InitialContext();
                cartService = (CartService) ic.lookup("java:global/techmart-ear-1.0/ejb/CartServiceBean");
                session.setAttribute("cartService", cartService);
            }

            String productIdStr = request.getParameter("productId");
            Integer productId = Integer.parseInt(productIdStr);

            if ("/add-to-cart".equals(action)) {
                String quantityStr = request.getParameter("quantity");
                Integer quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 1;

                CartItemRequestDto requestDto = CartItemRequestDto.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build();
                cartService.addItem(requestDto);

            } else if ("/update-cart".equals(action)) {
                String quantityStr = request.getParameter("quantity");
                Integer quantity = Integer.parseInt(quantityStr);
                cartService.updateQuantity(productId, quantity);

            } else if ("/remove-from-cart".equals(action)) {
                cartService.removeItem(productId);
            }

            int newCartSize = cartService.getCartSize();
            String totalAmount = cartService.getTotalAmount().toString();

            response.getWriter().write("{\"status\":\"success\", \"cartSize\":" + newCartSize + ", \"totalAmount\":\"" + totalAmount + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Operation failed\"}");
        }
    }
}