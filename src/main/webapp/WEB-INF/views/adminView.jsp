<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
  <jsp:include page="_meta.jsp"/>
  <title>Trang chủ Admin</title>
</head>

<body>
<jsp:include page="_headerAdmin.jsp"/>

<section class="section-content padding-y">
  <div class="container">
    <div class="card bg-light">
      <div class="card-body p-5">
        <h1 class="display-5 mb-5">Shop laptop 🌼</h1>
        <div class="row">
          <div class="col-6 col-lg-3">
            <figure class="card">
              <div class="p-3">
                <h4 class="title">${requestScope.totalUsers}</h4>
                <span>người dùng</span>
              </div>
            </figure>
          </div>
          <div class="col-6 col-lg-3">
            <figure class="card">
              <div class="p-3">
                <h4 class="title">${requestScope.totalCategories}</h4>
                <span>thể dòng laptop</span>
              </div>
            </figure>
          </div>
          <div class="col-6 col-lg-3">
            <figure class="card">
              <div class="p-3">
                <h4 class="title">${requestScope.totalProducts}</h4>
                <span>laptop</span>
              </div>
            </figure>
          </div>
          <div class="col-6 col-lg-3">
            <figure class="card">
              <div class="p-3">
                <h4 class="title">${requestScope.totalOrders}</h4>
                <span>đơn hàng</span>
              </div>
            </figure>
          </div>
        </div>
      </div>
    </div> <!-- card.// -->
  </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footerAdmin.jsp"/>
</body>

</html>
