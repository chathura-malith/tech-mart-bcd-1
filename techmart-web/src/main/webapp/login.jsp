<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
</head>
<body>

<%--<nav class="navbar navbar-expand-lg navbar-dark bg-dark">--%>
<%--    <div class="container">--%>
<%--        <a class="navbar-brand" href="index.jsp">TechMart Online</a>--%>
<%--    </div>--%>
<%--</nav>--%>

<jsp:include page="header.jsp" />

<div class="container login-container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow">
                <div class="card-body p-5">
                    <h2 class="text-center mb-4">Welcome Back</h2>

                    <c:if test="${param.success == 'registered'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            Registration successful! You can now login.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
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

                    <form action="<c:url value='/login-action'/>" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${loginDto.email}" placeholder="name@example.com" required>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Enter your password" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">Login</button>
                        </div>

                        <div class="text-center mt-4">
                            <p>Don't have an account? <a href="register.jsp" class="text-decoration-none">Register here</a></p>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>