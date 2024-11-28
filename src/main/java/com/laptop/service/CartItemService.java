package com.laptop.service;

import com.laptop.models.Cart;
import com.laptop.models.CartItem;
import com.laptop.dao.CartItemDAO;
import com.laptop.models.Product;
import com.laptop.models.User;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CartItemService extends CartItemDAO  {
    public CartItemService() {
    }


    public void insert(long cartId, long productId, int quantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Cart cart = session.get(Cart.class, cartId);
            Product product = session.get(Product.class, productId);
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setUpdatedAt(LocalDateTime.now());
            session.save(cartItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
