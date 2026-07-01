package com.techmart.filter;

import com.techmart.core.dto.response.UserResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin-dashboard", "/add-product", "/add-product.jsp", "/add-category.jsp"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        UserResponseDto admin = (session != null) ? (UserResponseDto) session.getAttribute("admin") : null;

        if (admin != null && "ADMIN".equals(admin.getRole())) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/admin-login.jsp?error=unauthorized_admin");
        }
    }
}