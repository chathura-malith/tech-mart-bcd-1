package com.techmart.controller;

import com.techmart.core.dto.request.CategoryRequestDto;
import com.techmart.core.service.CategoryService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CategoryController", urlPatterns = {"/add-category"})
public class CategoryController extends HttpServlet {

    @EJB
    private CategoryService categoryService;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryRequestDto requestDto = CategoryRequestDto.builder()
                .name(request.getParameter("name"))
                .build();

        Set<ConstraintViolation<CategoryRequestDto>> violations = validator.validate(requestDto);
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<CategoryRequestDto> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errors", errorMessages);
            request.setAttribute("categoryDto", requestDto);
            request.getRequestDispatcher("add-category.jsp").forward(request, response);
            return;
        }

        try {
            categoryService.addCategory(requestDto);
            response.sendRedirect("add-category.jsp?success=true");

        } catch (jakarta.ejb.EJBException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                errorMessages.add(e.getCause().getMessage());
            } else {
                errorMessages.add("An unexpected database error occurred.");
            }

            request.setAttribute("errors", errorMessages);
            request.setAttribute("categoryDto", requestDto);
            request.getRequestDispatcher("add-category.jsp").forward(request, response);
        }
    }
}