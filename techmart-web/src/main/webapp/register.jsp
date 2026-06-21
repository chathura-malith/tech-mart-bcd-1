<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/register.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
</head>
<body>

<%--<nav class="navbar navbar-expand-lg navbar-dark bg-dark">--%>
<%--    <div class="container">--%>
<%--        <a class="navbar-brand" href="index.jsp">TechMart Online</a>--%>
<%--    </div>--%>
<%--</nav>--%>

<jsp:include page="header.jsp" />

<div class="container register-container">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow">
                <div class="card-body p-5">
                    <h2 class="text-center mb-4">Create an Account</h2>

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

                    <form action="register-action" method="POST">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="firstName" class="form-label">First Name</label>
                                <!-- value="${userDto.firstName}" මගින් error එකකදී පරණ දත්තය නැවත පෙන්වයි -->
                                <input type="text" class="form-control" id="firstName" name="firstName"
                                       value="${userDto.firstName}" placeholder="John" required>
                            </div>
                            <div class="col-md-6">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName"
                                       value="${userDto.lastName}" placeholder="Doe" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${userDto.email}" placeholder="name@example.com" required>
                        </div>

                        <div class="mb-3">
                            <label for="mobile" class="form-label">Mobile Number</label>
                            <input type="text" class="form-control" id="mobile" name="mobile"
                                   value="${userDto.mobile}" placeholder="07XXXXXXXX" required>
                            <div class="form-text">Format: 07XXXXXXXX (Sri Lankan mobile numbers only)</div>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Password</label>
                            <!-- ආරක්ෂාව සඳහා Password එක ආපසු පිරවෙන්නේ නැත -->
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Min 6 characters" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">Register</button>
                        </div>

                        <div class="text-center mt-3">
                            <p>Already have an account? <a href="login.jsp" class="text-decoration-none">Login here</a></p>
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