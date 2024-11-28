package com.laptop.dao;

import com.laptop.models.Cart;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CartDAO extends AbstractDAO<Cart> {
    public CartDAO() {
        super(Cart.class);
    }

    public Optional<Cart> getByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cart WHERE user.id = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .uniqueResultOptional();
        }
    }

    public int countCartItemQuantityByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT SUM(ci.quantity) FROM Cart c JOIN c.cartItems ci WHERE c.user.id = :userId")
                    .setParameter("userId", userId)
                    .uniqueResult()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public int countOrderByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.user.id = :userId")
                    .setParameter("userId", userId)
                    .uniqueResult()).intValue();
        }
    }

    public int countOrderDeliverByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.user.id = :userId AND o.status = 1")
                    .setParameter("userId", userId)
                    .uniqueResult()).intValue();
        }
    }

    public int countOrderReceivedByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT COUNT(o.id) FROM Order o WHERE o.user.id = :userId AND o.status = 2")
                    .setParameter("userId", userId)
                    .uniqueResult()).intValue();
        }
    }
}
