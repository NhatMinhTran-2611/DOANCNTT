package com.laptop.service;

import com.laptop.models.*;
import com.laptop.dao.OrderDAO;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService extends OrderDAO {
    public OrderService() {
    }

    public long insert(long userId, int status, int deliveryMethod, double deliveryPrice) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            Order order = new Order();
            order.setUser(user);
            order.setStatus(status);
            order.setDeliveryMethod(deliveryMethod);
            order.setDeliveryPrice(deliveryPrice);
            order.setCreatedAt(LocalDateTime.now());
            session.save(order);
            transaction.commit();
            return order.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0L;
        }

    }
}
