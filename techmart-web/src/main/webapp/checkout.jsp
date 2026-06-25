<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | TechMart Online</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="header.jsp" />

<div class="container my-5">
    <h2 class="mb-4 fw-bold">Checkout</h2>

    <div class="row">
        <div class="col-lg-8 mb-4">
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="fw-bold mb-0">Shipping Address</h4>
                        <button class="btn btn-outline-primary btn-sm rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#addAddressModal">
                            <i class="bi bi-plus-lg"></i> Add New Address
                        </button>
                    </div>

                    <form id="checkoutForm" action="${pageContext.request.contextPath}/place-order" method="POST">

                        <c:choose>
                            <c:when test="${not empty addresses}">
                                <div class="row g-3">
                                    <c:forEach items="${addresses}" var="address" varStatus="loop">
                                        <div class="col-md-6">
                                            <div class="card border ${loop.index == 0 ? 'border-warning shadow-sm' : ''} h-100 address-card cursor-pointer">
                                                <div class="card-body">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="radio" name="selectedAddressId" id="address${address.id}" value="${address.id}" ${loop.index == 0 ? 'checked' : ''}>
                                                        <label class="form-check-label w-100" for="address${address.id}">
                                                            <strong>${address.streetAddress}</strong><br>
                                                                ${address.city}<br>
                                                            Postal Code: ${address.postalCode}
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-warning text-center">
                                    You don't have any saved addresses. Please add a new address to continue.
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </form>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card border-0 shadow-sm rounded-4 sticky-top" style="top: 100px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-4">Order Summary</h5>

                    <div class="d-flex justify-content-between mb-3 text-secondary">
                        <span>Items (${sessionScope.cartService.cartSize})</span>
                        <span>LKR ${sessionScope.cartService.totalAmount}.00</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3 text-secondary">
                        <span>Shipping</span>
                        <span class="text-success fw-semibold">Free</span>
                    </div>

                    <hr class="my-4">

                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <span class="fs-5 fw-bold text-dark">Total</span>
                        <span class="fs-4 fw-bold text-danger">LKR ${sessionScope.cartService.totalAmount}.00</span>
                    </div>

                    <button type="submit" form="checkoutForm" class="btn btn-warning w-100 py-3 fw-bold mb-3 rounded-3 ${empty addresses ? 'disabled' : ''}">
                        Confirm & Place Order
                    </button>
                    <small class="text-muted text-center d-block">By placing your order, you agree to our Terms and Conditions.</small>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addAddressModal" tabindex="-1" aria-labelledby="addAddressModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content rounded-4 border-0 shadow">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title fw-bold" id="addAddressModalLabel">Add New Address</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div id="addressErrorAlert" class="alert alert-danger d-none small py-2" role="alert"></div>

                <form id="newAddressForm">
                    <div class="mb-3">
                        <label class="form-label text-muted small">Street Address</label>
                        <input type="text" class="form-control" id="streetAddress" required>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label text-muted small">City</label>
                            <input type="text" class="form-control" id="city" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label text-muted small">Postal Code</label>
                            <input type="text" class="form-control" id="postalCode" required>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer border-top-0">
                <button type="button" class="btn btn-light rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary rounded-pill px-4" onclick="saveNewAddress()">Save Address</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/js/checkout.js"></script>

</body>
</html>