<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | TechMart Online</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/admin-dashboard.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="admin-header.jsp" />

<div class="container">
    <h2 class="fw-bold mb-4">System Performance Overview</h2>

    <div class="row g-4">
        <div class="col-md-3">
            <div class="card metric-card bg-white shadow-sm h-100 p-3">
                <div class="d-flex align-items-center">
                    <div class="icon-box bg-primary bg-opacity-10 text-primary me-3">
                        <i class="bi bi-stopwatch-fill"></i>
                    </div>
                    <div>
                        <h6 class="text-muted mb-1">Avg Response Time</h6>
                        <h3 class="fw-bold mb-0">${avgResponseTime} ms</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card metric-card bg-white shadow-sm h-100 p-3">
                <div class="d-flex align-items-center">
                    <div class="icon-box bg-success bg-opacity-10 text-success me-3">
                        <i class="bi bi-hdd-network-fill"></i>
                    </div>
                    <div>
                        <h6 class="text-muted mb-1">Total EJB Requests</h6>
                        <h3 class="fw-bold mb-0">${totalRequests}</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card metric-card bg-white shadow-sm h-100 p-3">
                <div class="d-flex align-items-center">
                    <div class="icon-box bg-warning bg-opacity-10 text-warning me-3">
                        <i class="bi bi-people-fill"></i>
                    </div>
                    <div>
                        <h6 class="text-muted mb-1">Active Users</h6>
                        <h3 class="fw-bold mb-0">${activeUsers}</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card metric-card bg-white shadow-sm h-100 p-3">
                <div class="d-flex align-items-center">
                    <div class="icon-box bg-danger bg-opacity-10 text-danger me-3">
                        <i class="bi bi-bag-check-fill"></i>
                    </div>
                    <div>
                        <h6 class="text-muted mb-1">Total Items Sold</h6>
                        <h3 class="fw-bold mb-0">${itemsSold}</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>