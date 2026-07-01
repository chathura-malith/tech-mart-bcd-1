package com.techmart.controller;

import com.techmart.core.service.SystemMetricsService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin-dashboard"})
public class AdminDashboardController extends HttpServlet {

    @EJB
    private SystemMetricsService metricsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("avgResponseTime", String.format("%.2f", metricsBean.getAverageResponseTime()));
        request.setAttribute("totalRequests", metricsBean.getTotalRequests());
        request.setAttribute("activeUsers", metricsBean.getActiveUsers());
        request.setAttribute("itemsSold", metricsBean.getItemsSold());

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
}