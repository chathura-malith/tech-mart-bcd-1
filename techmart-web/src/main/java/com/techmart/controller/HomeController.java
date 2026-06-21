package com.techmart.controller;

import com.techmart.core.dto.response.CategoryResponseDto;
import com.techmart.core.dto.response.ProductResponseDto;
import com.techmart.core.service.CategoryService;
import com.techmart.core.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @EJB
    private ProductService productService;

    @EJB
    private CategoryService categoryService;

    private static final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String categoryIdParam = request.getParameter("categoryId");
        String pageParam = request.getParameter("page");

        Integer categoryId = null;
        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdParam);
            } catch (NumberFormatException ignored) {
            }
        }

        int page = 1;
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException ignored) {}
        }

        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        List<ProductResponseDto> products = productService.searchProducts(keyword, categoryId, page, PAGE_SIZE);
        long totalProducts = productService.getProductTotalCount(keyword, categoryId);

        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("selectedCategory", categoryId);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}