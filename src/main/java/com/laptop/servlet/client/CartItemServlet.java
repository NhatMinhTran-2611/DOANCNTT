package com.laptop.servlet.client;

import com.laptop.models.Cart;
import com.laptop.models.CartItem;
import com.laptop.models.Product;
import com.laptop.models.User;
import com.laptop.dto.CartItemRequest;
import com.laptop.dto.CartItemResponse;
import com.laptop.dto.CartResponse;
import com.laptop.dto.ErrorMessage;
import com.laptop.dto.SuccessMessage;
import com.laptop.service.CartItemService;
import com.laptop.service.CartService;
import com.laptop.service.UserService;
import com.laptop.utils.JsonUtils;
import com.laptop.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "CartItemServlet", value = "/cartItem")
public class CartItemServlet extends HttpServlet {
    private final CartService cartService = new CartService();
    private final CartItemService cartItemService = new CartItemService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy userId và đối tượng user từ database theo userId này
        long userId = Protector.of(() -> Long.parseLong(request.getParameter("userId"))).get(0L);
        Optional<User> userFromServer = Protector.of(() -> userService.getById(userId)).get(Optional::empty);

        // Nếu userId là số nguyên dương và có hiện diện trong bảng user
        if (userId > 0L && userFromServer.isPresent()) {
            // Lấy đối tượng cart từ database theo userId
            Optional<Cart> cartFromServer = Protector.of(() -> cartService.getByUserId(userId)).get(Optional::empty);

            // Nếu cart của user này đã có trong database
            if (cartFromServer.isPresent()) {
                long cartId = cartFromServer.get().getId();
                List<CartItem> cartItems = Protector.of(() -> cartItemService.getByCartId(cartId)).get(ArrayList::new);

                List<CartItemResponse> cartItemResponses = cartItems.stream().map(cartItem -> new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getCart().getId(),
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getProduct().getDiscount(),
                        cartItem.getProduct().getQuantity(),
                        cartItem.getProduct().getImageName(),
                        cartItem.getQuantity()
                )).collect(Collectors.toList());

                CartResponse cartResponse = new CartResponse(cartId, userId, cartItemResponses);
                JsonUtils.out(response, cartResponse, HttpServletResponse.SC_OK);
            } else {
                CartResponse cartResponse = new CartResponse(0L, userId, Collections.emptyList());
                JsonUtils.out(response, cartResponse, HttpServletResponse.SC_OK);
            }
        } else {
            String errorMessage = "Đã có lỗi truy vấn!";
            JsonUtils.out(response, new ErrorMessage(404, errorMessage), HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy đối tượng cartItemRequest từ JSON trong request
        CartItemRequest cartItemRequest = JsonUtils.get(request, CartItemRequest.class);

        // Lấy đối tượng cart từ database theo userId từ cartItemRequest
        Optional<Cart> cartFromServer = Protector.of(() -> cartService.getByUserId(cartItemRequest.getUserId()))
                .get(Optional::empty);

        // Nhận cartId từ cartFromServer (nếu đã có) hoặc cart mới (nếu chưa có)
        long cartId;

        if (cartFromServer.isPresent()) {
            cartId = cartFromServer.get().getId();
        } else {
            long userId = cartItemRequest.getUserId();
            User user = userService.getById(userId).orElse(null);
            Cart cart = new Cart(user, LocalDateTime.now(), null);
            cartId = Protector.of(() -> cartService.insert(cart)).get(0L);
        }

        String successMessage = "Đã thêm sản phẩm vào giỏ hàng thành công!";
        String errorMessage = "Đã có lỗi truy vấn!";

        Runnable doneFunction = () -> JsonUtils.out(
                response,
                new SuccessMessage(200, successMessage),
                HttpServletResponse.SC_OK);
        Runnable failFunction = () -> JsonUtils.out(
                response,
                new ErrorMessage(404, errorMessage),
                HttpServletResponse.SC_NOT_FOUND);

        // Nếu cart của user này đã có trong database (cardId lớn hơn O)
        if (cartId > 0L) {
            // Lấy đối tượng cartItem từ database theo cartId và productId của cartItemRequest
            Optional<CartItem> cartItemFromServer = Protector.of(() -> cartItemService.getByCartIdAndProductId(
                    cartId, cartItemRequest.getProductId()
            )).get(Optional::empty);

            // Nếu cartItem của cartId và productId này đã có trong database
            if (cartItemFromServer.isPresent()) {
                CartItem cartItem = cartItemFromServer.get();
                cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
                cartItem.setUpdatedAt(LocalDateTime.now());
                Protector.of(() -> cartItemService.update(cartItem))
                        .done(r -> doneFunction.run())
                        .fail(e -> failFunction.run());
            } else {

                Protector.of(() -> cartItemService.insert(cartId, cartItemRequest.getProductId(), cartItemRequest.getQuantity()))
                        .done(r -> doneFunction.run())
                        .fail(e -> failFunction.run());
            }
        } else {
            failFunction.run();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartItemRequest cartItemRequest = JsonUtils.get(request, CartItemRequest.class);

        long cartItemId = Protector.of(() -> Long.parseLong(request.getParameter("cartItemId"))).get(0L);
        Optional<CartItem> cartItemFromServer = Protector.of(() -> cartItemService.getById(cartItemId)).get(Optional::empty);

        String successMessage = "Đã cập nhật số lượng của sản phẩm thành công!";
        String errorMessage = "Đã có lỗi truy vấn!";

        Runnable doneFunction = () -> JsonUtils.out(
                response,
                new SuccessMessage(200, successMessage),
                HttpServletResponse.SC_OK);
        Runnable failFunction = () -> JsonUtils.out(
                response,
                new ErrorMessage(404, errorMessage),
                HttpServletResponse.SC_NOT_FOUND);

        if (cartItemId > 0L && cartItemFromServer.isPresent()) {
            CartItem cartItem = cartItemFromServer.get();
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setUpdatedAt(LocalDateTime.now());
            Protector.of(() -> cartItemService.update(cartItem))
                    .done(r -> doneFunction.run())
                    .fail(e -> failFunction.run());
        } else {
            failFunction.run();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long cartItemId = Protector.of(() -> Long.parseLong(request.getParameter("cartItemId"))).get(0L);

        String successMessage = "Đã xóa sản phẩm khỏi giỏ hàng thành công!";
        String errorMessage = "Đã có lỗi truy vấn!";

        Runnable doneFunction = () -> JsonUtils.out(
                response,
                new SuccessMessage(200, successMessage),
                HttpServletResponse.SC_OK);
        Runnable failFunction = () -> JsonUtils.out(
                response,
                new ErrorMessage(404, errorMessage),
                HttpServletResponse.SC_NOT_FOUND);

        if (cartItemId > 0L) {
            Protector.of(() -> cartItemService.delete(cartItemId))
                    .done(r -> doneFunction.run())
                    .fail(e -> failFunction.run());
        } else {
            failFunction.run();
        }
    }
}
