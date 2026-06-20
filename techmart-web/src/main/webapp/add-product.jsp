<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product | TechMart Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/add-product.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">TechMart Admin</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="add-category.jsp">Categories</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container admin-container">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7">
            <div class="card shadow">
                <div class="card-body p-5">
                    <h2 class="text-center mb-4">Add New Product</h2>

                    <c:if test="${param.success == 'true'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            Product and image uploaded successfully!
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger" role="alert">
                            <h6 class="alert-heading">Please correct the following errors:</h6>
                            <ul class="mb-0">
                                <c:forEach items="${errors}" var="error">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <form action="<c:url value='/add-product'/>" method="POST" enctype="multipart/form-data">

                        <div class="mb-3">
                            <label for="name" class="form-label">Product Name</label>
                            <input type="text" class="form-control" id="name" name="name"
                                   value="${productDto.name}" placeholder="e.g. Asus ROG Strix" required>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="price" class="form-label">Price (LKR)</label>
                                <input type="number" step="0.01" class="form-control" id="price" name="price"
                                       value="${productDto.price}" placeholder="0.00" required>
                            </div>
                            <div class="col-md-6">
                                <label for="stockQuantity" class="form-label">Stock Quantity</label>
                                <input type="number" class="form-control" id="stockQuantity" name="stockQuantity"
                                       value="${productDto.stockQuantity}" placeholder="0" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="categoryId" class="form-label">Category</label>
                            <select class="form-select" id="categoryId" name="categoryId" required>
                                <option value="" disabled ${empty productDto.categoryId ? 'selected' : ''}>Select a category</option>
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.id}" ${productDto.categoryId == cat.id ? 'selected' : ''}>
                                            ${cat.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="image" class="form-label">Product Image</label>
                            <input class="form-control" type="file" id="image" name="image" accept="image/png, image/jpeg, image/jpg" required>
                            <div class="form-text text-muted">
                                Accepted formats: .jpg, .jpeg, .png (Max 10MB). <br>
                                <small class="text-danger">* If a validation error occurs, you will need to re-select the image.</small>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-success btn-lg">Upload Product</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>