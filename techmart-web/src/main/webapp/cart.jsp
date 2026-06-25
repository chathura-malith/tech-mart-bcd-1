
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart | TechMart Online</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="css/cart.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="header.jsp" />

<div class="container my-5">
    <h2 class="mb-4 fw-bold">Your Shopping Cart</h2>

    <div id="cart-content">
        <c:choose>
            <c:when test="${empty sessionScope.cartService or sessionScope.cartService.cartSize == 0}">
                <div class="text-center py-5 bg-white shadow-sm rounded-4 border-0">
                    <i class="bi bi-cart-x text-muted" style="font-size: 5rem;"></i>
                    <h3 class="mt-3 fw-bold text-secondary">Your cart is empty</h3>
                    <p class="text-muted mb-4">Looks like you haven't added anything to your cart yet.</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-warning px-5 py-2 fw-bold">
                        Start Shopping
                    </a>
                </div>
            </c:when>

            <c:otherwise>
                <div class="row">
                    <div class="col-lg-8 mb-4">
                        <div class="card border-0 shadow-sm rounded-4">
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-borderless table-hover align-middle mb-0">
                                        <thead class="table-light border-bottom">
                                        <tr>
                                            <th scope="col" class="ps-4 py-3">Product</th>
                                            <th scope="col" class="py-3">Price</th>
                                            <th scope="col" class="text-center py-3">Quantity</th>
                                            <th scope="col" class="py-3">Subtotal</th>
                                            <th scope="col" class="pe-4 py-3"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${sessionScope.cartService.cartItems}" var="item">

                                            <tr class="border-bottom" id="product-row-${item.productId}">
                                                <td class="ps-4 py-3">
                                                    <div class="d-flex align-items-center gap-3">
                                                        <img src="${pageContext.request.contextPath}/image-service?file=${item.imageUrl}"
                                                             alt="${item.productName}" class="cart-img rounded border">
                                                        <div>
                                                            <h6 class="mb-0 fw-bold text-dark">${item.productName}</h6>
                                                            <small class="text-muted">Product ID: #${item.productId}</small>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td class="fw-semibold text-secondary">
                                                    LKR <span id="price-${item.productId}">${item.price}.00</span>
                                                </td>

                                                <td class="text-center">
                                                    <div class="d-inline-flex align-items-center border rounded-pill bg-light p-1">
                                                        <button onclick="changeQty(${item.productId}, -1)" class="btn btn-sm btn-light rounded-circle p-1" style="width:28px; height:28px;">
                                                            <i class="bi bi-dash fs-6"></i>
                                                        </button>
                                                        <input type="text" id="qty-${item.productId}" class="qty-input" value="${item.quantity}" readonly>
                                                        <button onclick="changeQty(${item.productId}, 1)" class="btn btn-sm btn-light rounded-circle p-1" style="width:28px; height:28px;">
                                                            <i class="bi bi-plus fs-6"></i>
                                                        </button>
                                                    </div>
                                                </td>

                                                <td class="fw-bold text-dark">
                                                    LKR <span id="subtotal-${item.productId}">${item.price * item.quantity}.00</span>
                                                </td>
                                                <td class="pe-4 text-end">
                                                    <button onclick="removeCartItem(${item.productId})" class="btn btn-outline-danger btn-sm rounded-circle" title="Remove Item">
                                                        <i class="bi bi-trash3"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="card border-0 shadow-sm rounded-4 sticky-top" style="top: 100px;">
                            <div class="card-body p-4">
                                <h5 class="fw-bold mb-4">Order Summary</h5>

                                <div class="d-flex justify-content-between mb-3 text-secondary">
                                    <span>Total Items</span>
                                    <span id="summary-items">${sessionScope.cartService.cartSize}</span>
                                </div>
                                <div class="d-flex justify-content-between mb-3 text-secondary">
                                    <span>Shipping</span>
                                    <span class="text-success fw-semibold">Free Delivery</span>
                                </div>

                                <hr class="my-4">

                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <span class="fs-5 fw-bold text-dark">Total</span>
                                    <span class="fs-4 fw-bold text-danger">LKR <span id="summary-total">${sessionScope.cartService.totalAmount}.00</span></span>
                                </div>

                                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-dark w-100 py-3 fw-bold mb-3 rounded-3">
                                    Proceed to Checkout
                                </a>

                                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary w-100 py-2 fw-semibold rounded-3">
                                    Continue Shopping
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="footer.jsp" />
<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/js/cart.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>