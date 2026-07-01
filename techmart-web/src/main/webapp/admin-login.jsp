<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
</head>
<body class="d-flex flex-column min-vh-100 bg-dark"> <div class="container login-container my-auto">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5 mt-5">
            <div class="card shadow border-0 rounded-4">
                <div class="card-body p-5">
                    <div class="text-center mb-4">
                        <i class="bi bi-shield-lock-fill text-warning" style="font-size: 3rem;"></i>
                        <h2 class="fw-bold mt-2">Admin Portal</h2>
                        <p class="text-muted">Secure Login Area</p>
                    </div>

                    <c:if test="${param.error == 'unauthorized_admin'}">
                        <div class="alert alert-warning" role="alert">
                            <i class="bi bi-exclamation-triangle-fill"></i> Please login to access the admin area.
                        </div>
                    </c:if>

                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger" role="alert">
                            <ul class="mb-0">
                                <c:forEach items="${errors}" var="error">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <form action="<c:url value='/admin-login-action'/>" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label fw-bold">Administrator Email</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${loginDto.email}" placeholder="admin@techmart.com" required>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label fw-bold">Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Enter your password" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-warning btn-lg fw-bold">Secure Login</button>
                        </div>

                        <div class="text-center mt-4">
                            <a href="index.jsp" class="text-secondary text-decoration-none"><i class="bi bi-arrow-left"></i> Back to Main Site</a>
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