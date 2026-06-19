package com.techmart.controller;

import com.techmart.core.dto.request.LoginRequestDto;
import com.techmart.core.dto.request.UserRequestDto;
import com.techmart.core.dto.response.UserResponseDto;
import com.techmart.core.service.AuthService;
import com.techmart.core.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UserController", urlPatterns = {"/register-action", "/login-action"})
public class UserController extends HttpServlet {

    @EJB
    private UserService userService;

    @EJB
    private AuthService authService;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/register-action".equals(action)) {
            registerUser(request, response);
        } else if ("/login-action".equals(action)) {
            loginUser(request, response);
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        UserRequestDto requestDto = UserRequestDto.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .email(request.getParameter("email"))
                .mobile(request.getParameter("mobile"))
                .password(request.getParameter("password"))
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto);
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<UserRequestDto> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        if (requestDto.getEmail() != null && !requestDto.getEmail().trim().isEmpty()) {
            if (userService.getUserByEmail(requestDto.getEmail()) != null) {
                errorMessages.add("This email address is already registered. Please login instead.");
            }
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errors", errorMessages);
            request.setAttribute("userDto", requestDto);

            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        userService.registerUser(requestDto);
        response.sendRedirect("login.jsp?success=registered");
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LoginRequestDto loginDto = LoginRequestDto.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginDto);
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<LoginRequestDto> violation : violations) {
            errorMessages.add(violation.getMessage());
        }


        if (!errorMessages.isEmpty()) {
            request.setAttribute("errors", errorMessages);
            request.setAttribute("loginDto", loginDto);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        UserResponseDto loggedUser = authService.login(loginDto);

        if (loggedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loggedUser);
            response.sendRedirect("index.jsp");
        } else {
            errorMessages.add("Invalid email address or password.");
            request.setAttribute("errors", errorMessages);
            request.setAttribute("loginDto", loginDto);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}