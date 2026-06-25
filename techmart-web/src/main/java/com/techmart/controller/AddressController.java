package com.techmart.controller;

import com.techmart.core.dto.request.AddressRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.service.AddressService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "AddressController", urlPatterns = {"/add-address"})
public class AddressController extends HttpServlet {

    @EJB
    private AddressService addressService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.getWriter().write("{\"status\":\"unauthorized\"}");
                return;
            }

            UserResponseDto user = (UserResponseDto) session.getAttribute("user");

            AddressRequestDto addressRequestDto = AddressRequestDto.builder()
                    .streetAddress(request.getParameter("streetAddress"))
                    .city(request.getParameter("city"))
                    .postalCode(request.getParameter("postalCode"))
                    .build();

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<AddressRequestDto>> violations = validator.validate(addressRequestDto);

            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"" + errorMessage + "\"}");
                return;
            }

            addressService.addAddress(user.getId(), addressRequestDto);
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to save address\"}");
        }
    }
}