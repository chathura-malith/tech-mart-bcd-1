<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Successful | TechMart Online</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
  <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="css/order-sucess.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="header.jsp" />

<div class="container my-5 text-center flex-grow-1 d-flex flex-column justify-content-center align-items-center">
  <div class="card border-0 shadow-sm rounded-4 p-5" style="max-width: 600px;">
    <i class="bi bi-check-circle-fill success-icon mb-3"></i>
    <h2 class="fw-bold mb-3">Order Placed Successfully!</h2>
    <p class="text-muted mb-4 fs-5">
      Thank you for shopping with TechMart Online. Your order has been received and is being processed in the background.
    </p>

    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
      <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-warning btn-lg px-4 rounded-pill fw-bold shadow-sm">
        <i class="bi bi-house-door me-2"></i> Back to Home
      </a>
      <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-dark btn-lg px-4 rounded-pill fw-bold shadow-sm">
        Continue Shopping
      </a>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>