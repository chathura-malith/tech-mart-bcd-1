package com.techmart.controller;

import com.techmart.core.dto.request.ProductRequestDto;
import com.techmart.core.dto.response.CategoryResponseDto;
import com.techmart.core.service.CategoryService;
import com.techmart.core.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "ProductController", urlPatterns = {"/add-product"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ProductController extends HttpServlet {

    @EJB
    private ProductService productService;

    @EJB
    private CategoryService categoryService;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private static final String UPLOAD_DIR = "D:/Java Institute/BCD-1/FinalAssessment/Project/techmart/product-images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {// Form එක Load වෙද්දී Categories ටික Database එකෙන් අරන් JSP එකට යවනවා
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("add-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errorMessages = new ArrayList<>();

        BigDecimal price = null;
        Integer stockQuantity = null;
        Integer categoryId = null;

        try {
            if (!request.getParameter("price").isEmpty()) price = new BigDecimal(request.getParameter("price"));
            if (!request.getParameter("stockQuantity").isEmpty()) stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
            if (!request.getParameter("categoryId").isEmpty()) categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException e) {
            errorMessages.add("Invalid number format for Price, Stock, or Category.");
        }

        String imageFileName = null;
        try {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String originalFileName = filePart.getSubmittedFileName();
                String fileExtension = "";
                if (originalFileName != null && originalFileName.contains(".")) {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }

                imageFileName = UUID.randomUUID().toString() + fileExtension;

                java.io.InputStream fileContent = filePart.getInputStream();
                java.nio.file.Files.copy(fileContent, new File(uploadDir, imageFileName).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            } else {
                errorMessages.add("Product image is required.");
            }
        } catch (Exception e) {
            errorMessages.add("Image upload failed: " + e.getMessage());
        }

        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name(request.getParameter("name"))
                .price(price)
                .stockQuantity(stockQuantity)
                .categoryId(categoryId)
                .imageUrl(imageFileName)
                .build();


        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(requestDto);
        for (ConstraintViolation<ProductRequestDto> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errors", errorMessages);
            request.setAttribute("productDto", requestDto);
            request.setAttribute("categories", categoryService.getAllCategories());
            request.getRequestDispatcher("add-product.jsp").forward(request, response);
            return;
        }

        try {
            productService.addProduct(requestDto);
            response.sendRedirect("add-product?success=true");
        } catch (EJBException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                errorMessages.add(e.getCause().getMessage());
            } else {
                errorMessages.add("An unexpected database error occurred.");
            }
            request.setAttribute("errors", errorMessages);
            request.setAttribute("productDto", requestDto);
            request.setAttribute("categories", categoryService.getAllCategories());
            request.getRequestDispatcher("add-product.jsp").forward(request, response);
        }
    }
}