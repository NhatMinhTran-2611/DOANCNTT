<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html lang="vi">

<head>
  <jsp:include page="_meta.jsp"/>
  <title>Thêm sản phẩm</title>
</head>

<body>
<jsp:include page="_headerAdmin.jsp"/>

<section class="section-content">
  <div class="container">
    <header class="section-heading py-4">
      <h3 class="section-title">Thêm sản phẩm</h3>
    </header> <!-- section-heading.// -->

    <main class="row mb-5">
      <form class="col-lg-6" method="POST" action="${pageContext.request.contextPath}/admin/productManager/create"
            enctype="multipart/form-data">
        <c:if test="${not empty requestScope.successMessage}">
          <div class="alert alert-success mb-3" role="alert">
              ${requestScope.successMessage}
          </div>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
          <div class="alert alert-danger mb-3" role="alert">
              ${requestScope.errorMessage}
          </div>
        </c:if>
        <div class="mb-3">
          <label for="product-name" class="form-label">Tên sản phẩm <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.nameViolations
                   ? 'is-invalid' : (not empty requestScope.product.name ? 'is-valid' : '')}"
                 id="product-name"
                 name="name"
                 value="${requestScope.product.name}"
                 required>
          <c:if test="${not empty requestScope.violations.nameViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.nameViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-category" class="form-label">Thể loại <span class="text-danger">*</span></label>
          <select class="form-select ${not empty requestScope.violations.categoryViolations
                    ? 'is-invalid' : (not empty requestScope.categoryId ? 'is-valid' : '')}"
                  id="product-category"
                  name="category"
                  required>
            <option ${empty requestScope.categoryId || requestScope.categoryId == 0 ? 'selected' : ''} disabled>
              Chọn một thể loại...
            </option>
            <c:forEach var="category" items="${requestScope.categories}">
              <option value="${category.id}" ${requestScope.categoryId == category.id ? 'selected' : ''}>
                  ${category.name}
              </option>
            </c:forEach>
          </select>
          <c:if test="${not empty requestScope.violations.categoryViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.categoryViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-price" class="form-label">Giá gốc <span class="text-danger">*</span></label>
          <div class="input-group ${not empty requestScope.violations.priceViolations ? 'has-validation' : ''}">
            <input type="number"
                   class="form-control ${not empty requestScope.violations.priceViolations
                     ? 'is-invalid' : (not empty requestScope.product.price ? 'is-valid' : '')}"
                   id="product-price"
                   name="price"
                   value="${requestScope.product.price}"
                   min="0"
                   step="500"
                   required>
            <span class="input-group-text">₫</span>
            <c:if test="${not empty requestScope.violations.priceViolations}">
              <div class="invalid-feedback">
                <ul class="list-unstyled mb-0">
                  <c:forEach var="violation" items="${requestScope.violations.priceViolations}">
                    <li>${violation}</li>
                  </c:forEach>
                </ul>
              </div>
            </c:if>
          </div>
        </div>
        <div class="mb-3">
          <label for="product-ram" class="form-label">RAM <span class="text-danger">*</span></label>
          <div class="input-group ${not empty requestScope.violations.ramViolations ? 'has-validation' : ''}">
            <input type="text"
                   class="form-control ${not empty requestScope.violations.ramViolations
                     ? 'is-invalid' : (not empty requestScope.product.ram ? 'is-valid' : '')}"
                   id="product-ram"
                   name="ram"
                   value="${requestScope.product.ram}"
                   list="ram-options"
                   required>
            <span class="input-group-text">GB</span>
            <datalist id="ram-options">
              <option value="2GB">
              <option value="4GB">
              <option value="8GB">
            </datalist>
            <c:if test="${not empty requestScope.violations.ramViolations}">
              <div class="invalid-feedback">
                <ul class="list-unstyled">
                  <c:forEach var="violation" items="${requestScope.violations.ramViolations}">
                    <li>${violation}</li>
                  </c:forEach>
                </ul>
              </div>
            </c:if>
          </div>
        </div>
        <div class="mb-3">
          <label for="product-cpu" class="form-label">CPU <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.cpuViolations
                   ? 'is-invalid' : (not empty requestScope.product.cpu ? 'is-valid' : '')}"
                 id="product-cpu"
                 name="cpu"
                 value="${requestScope.product.cpu}"
                 required>
          <c:if test="${not empty requestScope.violations.cpuViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.cpuViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-vga" class="form-label">VGA <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.vgaViolations
                   ? 'is-invalid' : (not empty requestScope.product.vga ? 'is-valid' : '')}"
                 id="product-vga"
                 name="vga"
                 value="${requestScope.product.vga}"
                 required>
          <c:if test="${not empty requestScope.violations.vgaViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.vgaViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-weight" class="form-label">Weight <span class="text-danger">*</span></label>
          <div class="input-group ${not empty requestScope.violations.weightViolations ? 'has-validation' : ''}">
            <input type="number"
                   class="form-control ${not empty requestScope.violations.weightViolations
                     ? 'is-invalid' : (not empty requestScope.product.weight ? 'is-valid' : '')}"
                   id="product-weight"
                   name="weight"
                   value="${requestScope.product.weight}"
                   step="0.01"
                   required>
            <span class="input-group-text">kg</span>
            <c:if test="${not empty requestScope.violations.weightViolations}">
              <div class="invalid-feedback">
                <ul class="list-unstyled">
                  <c:forEach var="violation" items="${requestScope.violations.weightViolations}">
                    <li>${violation}</li>
                  </c:forEach>
                </ul>
              </div>
            </c:if>
          </div>
        </div>
        <div class="mb-3">
          <label for="product-os" class="form-label">OS <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.osViolations
                   ? 'is-invalid' : (not empty requestScope.product.os ? 'is-valid' : '')}"
                 id="product-os"
                 name="os"
                 value="${requestScope.product.os}"
                 required>
          <c:if test="${not empty requestScope.violations.osViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.osViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-ssd" class="form-label">SSD <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.ssdViolations
                   ? 'is-invalid' : (not empty requestScope.product.ssd ? 'is-valid' : '')}"
                 id="product-ssd"
                 name="ssd"
                 value="${requestScope.product.ssd}"
                 required>
          <c:if test="${not empty requestScope.violations.ssdViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.ssdViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-screenSize" class="form-label">Screen Size <span class="text-danger">*</span></label>
          <input type="number"
                 class="form-control ${not empty requestScope.violations.screenSizeViolations
                   ? 'is-invalid' : (not empty requestScope.product.screenSize ? 'is-valid' : '')}"
                 id="product-screenSize"
                 name="screenSize"
                 value="${requestScope.product.screenSize}"
                 step="0.1"
                 required>
          <c:if test="${not empty requestScope.violations.screenSizeViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.screenSizeViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-brand" class="form-label">Thương hiệu <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.brandViolations
                   ? 'is-invalid' : (not empty requestScope.product.brand ? 'is-valid' : '')}"
                 id="product-brand"
                 name="brand"
                 value="${requestScope.product.brand}"
                 required>
          <c:if test="${not empty requestScope.violations.brandViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.brandViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-battery" class="form-label">Pin <span class="text-danger">*</span></label>
          <input type="text"
                 class="form-control ${not empty requestScope.violations.batteryViolations
                   ? 'is-invalid' : (not empty requestScope.product.battery ? 'is-valid' : '')}"
                 id="product-battery"
                 name="battery"
                 value="${requestScope.product.battery}"
                 required>
          <c:if test="${not empty requestScope.violations.batteryViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.batteryViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-color" class="form-label">Color <span class="text-danger">*</span></label>
          <input type="color"
                 class="form-control ${not empty requestScope.violations.colorViolations
                   ? 'is-invalid' : (not empty requestScope.product.color ? 'is-valid' : '')}"
                 id="product-color"
                 name="color"
                 value="${requestScope.product.color}"
                 required>
          <c:if test="${not empty requestScope.violations.colorViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.colorViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-discount" class="form-label">Khuyến mãi <span class="text-danger">*</span></label>
          <div class="input-group ${not empty requestScope.violations.discountViolations ? 'has-validation' : ''}">
            <input type="number"
                   class="form-control ${not empty requestScope.violations.discountViolations
                     ? 'is-invalid' : (not empty requestScope.product.discount ? 'is-valid' : '')}"
                   id="product-discount"
                   name="discount"
                   value="${requestScope.product.discount}"
                   min="0"
                   max="100"
                   required>
                <ul class="list-unstyled mb-0">
                  <c:forEach var="violation" items="${requestScope.violations.discountViolations}">
                    <li>${violation}</li>
                  </c:forEach>
                </ul>
              </div>
            </c:if>
          </div>
        </div>
        <div class="mb-3">
          <label for="product-description" class="form-label">Mô tả sản phẩm</label>
          <textarea class="form-control ${not empty requestScope.violations.descriptionViolations
                      ? 'is-invalid' : (not empty requestScope.product.description ? 'is-valid' : '')}"
                    id="product-description"
                    rows="10"
                    name="description">${requestScope.product.description}</textarea>
          <c:if test="${not empty requestScope.violations.descriptionViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.descriptionViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-imageName" class="form-label">Hình sản phẩm</label>
          <input type="file"
                 class="form-control"
                 id="product-imageName"
                 name="image"
                 accept="image/*">
        </div>
        <div class="mb-3">
          <label class="form-label d-block">Cho phép giao dịch? <span class="text-danger">*</span></label>
          <div class="form-check d-inline-block me-4">
            <input class="form-check-input ${not empty requestScope.violations.shopViolations
                     ? 'is-invalid' : (not empty requestScope.product.shop ? 'is-valid' : '')}"
                   type="radio"
                   name="shop"
                   id="product-shop-yes"
                   value="1" ${requestScope.product.shop == 1 ? 'checked' : ''}
                   required>
            <label class="form-check-label" for="product-shop-yes">Có</label>
          </div>
          <div class="form-check d-inline-block">
            <input class="form-check-input ${not empty requestScope.violations.shopViolations
                     ? 'is-invalid' : (not empty requestScope.product.shop ? 'is-valid' : '')}"
                   type="radio"
                   name="shop"
                   id="product-shop-no"
                   value="0" ${requestScope.product.shop == 0 ? 'checked' : ''}
                   required>
            <label class="form-check-label" for="product-shop-no">Không</label>
          </div>
          <c:if test="${not empty requestScope.violations.genderViolations}">
            <div class="is-invalid"></div>
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.genderViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-startsAt" class="form-label">Ngày bắt đầu khuyến mãi</label>
          <input type="datetime-local"
                 class="form-control ${not empty requestScope.violations.startsAtViolations
                   ? 'is-invalid' : (not empty requestScope.product.startsAt ? 'is-valid' : '')}"
                 id="product-startsAt"
                 name="startsAt"
                 value="${requestScope.product.startsAt}">
          <c:if test="${not empty requestScope.violations.startsAtViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.startsAtViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>
        <div class="mb-3">
          <label for="product-endsAt" class="form-label">Ngày kết thúc khuyến mãi</label>
          <input type="datetime-local"
                 class="form-control ${not empty requestScope.violations.endsAtViolations
                   ? 'is-invalid' : (not empty requestScope.product.endsAt ? 'is-valid' : '')}"
                 id="product-endsAt"
                 name="endsAt"
                 value="${requestScope.product.endsAt}">
          <c:if test="${not empty requestScope.violations.endsAtViolations}">
            <div class="invalid-feedback">
              <ul class="list-unstyled">
                <c:forEach var="violation" items="${requestScope.violations.endsAtViolations}">
                  <li>${violation}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </div>

        <button type="submit" class="btn btn-primary me-2 mb-3">
          Thêm
        </button>
        <button type="reset"
                class="btn btn-warning me-2 mb-3"
                onclick="return confirm('Bạn có muốn để giá trị mặc định?')">
          Mặc định
        </button>
        <a class="btn btn-danger mb-3"
           href="${pageContext.request.contextPath}/admin/productManager"
           role="button"
           onclick="return confirm('Bạn có muốn hủy?')">
          Hủy
        </a>
      </form>
    </main>
  </div> <!-- container.// -->
</section> <!-- section-content.// -->

<jsp:include page="_footerAdmin.jsp"/>
</body>

</html>
