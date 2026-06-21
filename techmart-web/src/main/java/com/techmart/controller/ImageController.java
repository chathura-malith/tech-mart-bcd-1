package com.techmart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "ImageController", urlPatterns = {"/image-service"})
public class ImageController extends HttpServlet {

    private static final String UPLOAD_DIR = "D:/Java Institute/BCD-1/FinalAssessment/Project/techmart/product-images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");

        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is required");
            return;
        }

        File imageFile = new File(UPLOAD_DIR, fileName);

        if (!imageFile.exists() || !imageFile.getParentFile().getAbsolutePath().equals(new File(UPLOAD_DIR).getAbsolutePath())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) imageFile.length());

        Files.copy(imageFile.toPath(), response.getOutputStream());
    }
}