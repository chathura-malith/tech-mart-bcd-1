<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
  <div class="container">
    <a class="navbar-brand fw-bold fs-4 text-warning" href="${pageContext.request.contextPath}/home">TechMart Online</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="mainNav">

      <form class="d-flex mx-auto my-2 my-lg-0 w-50" action="${pageContext.request.contextPath}/home" method="GET">

        <select class="form-select w-auto bg-light border-0" name="categoryId" style="border-top-right-radius: 0; border-bottom-right-radius: 0; cursor: pointer;">
          <option value="">All Categories</option>
          <c:forEach items="${categories}" var="cat">
            <option value="${cat.id}" ${selectedCategory == cat.id ? 'selected' : ''}>
                ${cat.name}
            </option>
          </c:forEach>
        </select>

        <input class="form-control border-0 rounded-0" type="search" name="keyword" value="${searchKeyword}" placeholder="Search for laptops, phones..." aria-label="Search">

        <button class="btn btn-warning px-4" type="submit" style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
          Search
        </button>
      </form>

      <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
        <li class="nav-item">
          <a class="nav-link active" href="${pageContext.request.contextPath}/home">Home</a>
        </li>

        <li class="nav-item mx-2">
          <a class="btn btn-outline-light position-relative d-flex align-items-center gap-2" href="#">
            <i class="bi bi-cart3 fs-5"></i> Cart
            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                0
            </span>
          </a>
        </li>

        <c:choose>

          <c:when test="${not empty sessionScope.user}">
            <li class="nav-item ms-2 dropdown">
              <a class="btn btn-outline-warning dropdown-toggle" href="#" id="userMenu" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person-circle"></i> Hi, ${sessionScope.user.firstName}
              </a>
              <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="userMenu">
                <li><a class="dropdown-item" href="#">My Orders</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout-action">Logout</a></li>
              </ul>
            </li>
          </c:when>

          <c:otherwise>
            <li class="nav-item ms-2">
              <a class="btn btn-warning px-4" href="${pageContext.request.contextPath}/login.jsp">Login</a>
            </li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>