
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (request.getAttribute("products") == null) {
        request.getRequestDispatcher("/home").forward(request, response);
        return;
    }
%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TechMart Online | Buy Latest Laptops, Mobiles & Accessories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="css/home.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div id="techMartCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#techMartCarousel" data-bs-slide-to="0" class="active" aria-current="true"
                aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#techMartCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#techMartCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner shadow-sm">
        <div class="carousel-item active" data-bs-interval="4000">
            <img src="https://images.unsplash.com/photo-1593640408182-31c70c8268f5?auto=format&fit=crop&w=1920&q=80"
                 class="d-block w-100" alt="Premium Laptops">
            <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Next-Gen Laptops</h2>
                <p class="fs-5">Power your productivity with our premium selection of notebooks.</p>
            </div>
        </div>
        <div class="carousel-item" data-bs-interval="4000">
            <img src="https://images.unsplash.com/photo-1616348436168-de43ad0db179?auto=format&fit=crop&w=1920&q=80"
                 class="d-block w-100" alt="Latest Smartphones">
            <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Flagship Smartphones</h2>
                <p class="fs-5">Experience the future with the newest mobile devices.</p>
            </div>
        </div>
        <div class="carousel-item" data-bs-interval="4000">
            <img src="https://images.unsplash.com/photo-1583394838336-acd977736f90?auto=format&fit=crop&w=1920&q=80"
                 class="d-block w-100" alt="Tech Accessories">
            <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Premium Accessories</h2>
                <p class="fs-5">Complete your setup with top-tier audio and smart wearables.</p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#techMartCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#techMartCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<div class="container my-5">

    <c:if test="${not empty searchKeyword or not empty selectedCategory}">
        <div class="alert alert-info border-0 shadow-sm rounded-3 mb-4">
            Showing results
            <c:if test="${not empty searchKeyword}"> for <strong>"${searchKeyword}"</strong></c:if>
            <c:if test="${not empty selectedCategory}"> in selected category</c:if>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-sm btn-outline-secondary float-end">Clear Search</a>
        </div>
    </c:if>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">

        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach items="${products}" var="product">
                    <div class="col">
                        <div class="card h-100 shadow-sm border-0 product-card position-relative">

                            <span class="category-badge shadow-sm">${product.categoryName}</span>

                            <img src="${pageContext.request.contextPath}/image-service?file=${product.imageUrl}"
                                 class="card-img-top" alt="${product.name}">

                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title text-truncate" title="${product.name}">${product.name}</h5>

                                <div class="mt-auto">
                                    <p class="price-tag mb-2">LKR ${product.price}.00</p>

                                    <c:choose>
                                        <c:when test="${product.stockQuantity > 0}">
                                            <p class="text-success small mb-3">✅ In Stock (${product.stockQuantity})</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="text-danger small mb-3">❌ Out of Stock</p>
                                        </c:otherwise>
                                    </c:choose>

                                    <button onclick="addToCart(${product.id})" class="btn btn-dark w-100 ${product.stockQuantity == 0 ? 'disabled' : ''}">
                                        Add to Cart
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <div class="col-12 text-center py-5">
                    <h3 class="text-muted">No products found 😢</h3>
                    <p class="text-muted">Try adjusting your search or category filter.</p>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

    <c:if test="${totalPages > 1}">
        <nav aria-label="Product Pagination" class="mt-5">
            <ul class="pagination justify-content-center">

                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link text-dark" href="?page=${currentPage - 1}&keyword=${searchKeyword}&categoryId=${selectedCategory}">Previous</a>
                </li>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link ${currentPage == i ? 'bg-dark border-dark text-white' : 'text-dark'}"
                           href="?page=${i}&keyword=${searchKeyword}&categoryId=${selectedCategory}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link text-dark" href="?page=${currentPage + 1}&keyword=${searchKeyword}&categoryId=${selectedCategory}">Next</a>
                </li>
            </ul>
        </nav>
    </c:if>

</div>

<div class="toast-container position-fixed bottom-0 end-0 p-3" style="z-index: 1050;">
    <div id="cartToast" class="toast align-items-center text-bg-success border-0 shadow-lg" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body fw-bold fs-6">
                <i class="bi bi-check-circle-fill me-2"></i> Item successfully added to cart!
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />


<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/js/home.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>