package com.laptop.servlet.admin;

import com.laptop.service.CategoryService;
import com.laptop.service.OrderService;
import com.laptop.service.ProductService;
import com.laptop.service.UserService;
import com.laptop.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final CategoryService categoryService = new CategoryService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalUsers = Protector.of(userService::count).get(0);
        int totalCategories = Protector.of(categoryService::count).get(0);
        int totalProducts = Protector.of(productService::count).get(0);
        int totalOrders = Protector.of(orderService::count).get(0);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalOrders", totalOrders);
        request.getRequestDispatcher("/WEB-INF/views/adminView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
