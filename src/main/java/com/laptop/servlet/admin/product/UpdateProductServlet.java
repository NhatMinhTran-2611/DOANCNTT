package com.laptop.servlet.admin.product;

import com.laptop.models.Category;
import com.laptop.models.Product;
import com.laptop.service.CategoryService;
import com.laptop.service.ProductService;
import com.laptop.utils.ImageUtils;
import com.laptop.utils.Protector;
import com.laptop.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "UploadProductServlet", value = "/admin/productManager/update")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class UpdateProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L);
        Optional<Product> productFromServer = Protector.of(() -> productService.getById(id)).get(Optional::empty);

        if (productFromServer.isPresent()) {
            List<Category> categories = Protector.of(categoryService::getAll).get(ArrayList::new);
            Optional<Category> categoryFromServer = Protector.of(() -> categoryService.getByProductId(id)).get(Optional::empty);

            request.setAttribute("product", productFromServer.get());
            request.setAttribute("categories", categories);
            categoryFromServer.ifPresent(category -> request.setAttribute("categoryId", category.getId()));
            request.getRequestDispatcher("/WEB-INF/views/updateProductView.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/productManager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        product.setId(Protector.of(() -> Long.parseLong(request.getParameter("id"))).get(0L));
        product.setName(request.getParameter("name"));
        product.setPrice(Protector.of(() -> Double.parseDouble(request.getParameter("price"))).get(0d));
        product.setDiscount(Protector.of(() -> Double.parseDouble(request.getParameter("discount"))).get(0d));
        product.setQuantity(Protector.of(() -> Integer.parseInt(request.getParameter("quantity"))).get(0));
        product.setTotalBuy(Protector.of(() -> Integer.parseInt(request.getParameter("totalBuy"))).get(0));

        // Laptop information
        product.setRAM(request.getParameter("ram"));
        product.setCPU(request.getParameter("cpu"));
        product.setVGA(request.getParameter("vga"));
        String weight = request.getParameter("weight");
        product.setWeight(Float.parseFloat(weight));
        product.setOS(request.getParameter("os"));
        product.setSSD(request.getParameter("ssd"));
        product.setScreenSize(Float.parseFloat(request.getParameter("screenSize")));
        product.setBrand(request.getParameter("brand"));
        product.setBattery(request.getParameter("battery"));
        product.setColor(request.getParameter("color"));

        product.setDescription(request.getParameter("description").trim().isEmpty()
                ? null : request.getParameter("description"));
        product.setImageName(request.getParameter("imageName").trim().isEmpty()
                ? null : request.getParameter("imageName"));
        product.setShop(Protector.of(() -> Integer.parseInt(request.getParameter("shop"))).get(1));
        product.setUpdatedAt(LocalDateTime.now());
        product.setStartsAt(request.getParameter("startsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("startsAt")));
        product.setEndsAt(request.getParameter("endsAt").trim().isEmpty()
                ? null : LocalDateTime.parse(request.getParameter("endsAt")));

        long categoryId = Protector.of(() -> Long.parseLong(request.getParameter("category"))).get(0L);
        String deleteImage = request.getParameter("deleteImage");

        Map<String, List<String>> violations = new HashMap<>();
        violations.put("nameViolations", Validator.of(product.getName())
                .isNotNullAndEmpty()
                .isNotBlankAtBothEnds()
                .isAtMostOfLength(100)
                .toList());
        violations.put("priceViolations", Validator.of(product.getPrice())
                .isNotNull()
                .isLargerThan(0, "Giá gốc")
                .toList());
        violations.put("discountViolations", Validator.of(product.getDiscount())
                .isNotNull()
                .isLargerThan(0, "Khuyến mãi")
                .isSmallerThan(100, "Khuyến mãi")
                .toList());
        violations.put("quantityViolations", Validator.of(product.getQuantity())
                .isNotNull()
                .isLargerThan(0, "Tồn kho")
                .toList());
        violations.put("totalBuyViolations", Validator.of(product.getTotalBuy())
                .isNotNull()
                .isLargerThan(0, "Lượt mua")
                .toList());
        violations.put("descriptionViolations", Validator.of(product.getDescription())
                .isAtMostOfLength(2000)
                .toList());
        violations.put("shopViolations", Validator.of(product.getShop())
                .isNotNull()
                .toList());
        violations.put("categoryViolations", Optional.of(categoryId).filter(id -> id == 0)
                .map(id -> Collections.singletonList("Phải chọn thể loại cho sản phẩm"))
                .orElseGet(Collections::emptyList));

        int sumOfViolations = violations.values().stream().mapToInt(List::size).sum();
        String successMessage = "Sửa thành công!";
        String errorMessage = "Sửa thất bại!";

        if (sumOfViolations == 0) {
            if (product.getImageName() != null) {
                String currentImageName = product.getImageName();
                if (deleteImage != null) {
                    ImageUtils.delete(currentImageName);
                    product.setImageName(null);
                }
                ImageUtils.upload(request).ifPresent(imageName -> {
                    ImageUtils.delete(currentImageName);
                    product.setImageName(imageName);
                });
            } else {
                ImageUtils.upload(request).ifPresent(product::setImageName);
            }

            Optional<Category> categoryFromServer = Protector.of(() -> categoryService.getByProductId(product.getId()))
                    .get(Optional::empty);

            Protector.of(() -> {
                        productService.update(product);
                        if (categoryFromServer.isPresent()) {
                            productService.updateProductCategory(product.getId(), categoryId);
                        } else {
                            productService.insertProductCategory(product.getId(), categoryId);
                        }
                    })
                    .done(r -> request.setAttribute("successMessage", successMessage))
                    .fail(e -> request.setAttribute("errorMessage", errorMessage));
        } else {
            request.setAttribute("violations", violations);
            request.setAttribute("deleteImage", deleteImage);
        }

        List<Category> categories = Protector.of(categoryService::getAll).get(ArrayList::new);
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);
        request.setAttribute("categoryId", categoryId);
        request.getRequestDispatcher("/WEB-INF/views/updateProductView.jsp").forward(request, response);
    }
}
