package com.laptop.dao;

import com.laptop.models.Order;
import com.laptop.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    @Override
    public long insert(Order order) {
        return super.insert(order);
    }

    @Override
    public void update(Order order) {
        super.update(order);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public Optional<Order> getById(long id) {
        return super.getById(id);
    }

    @Override
    public List<Order> getAll() {
        return super.getAll();
    }

    @Override
    public List<Order> getPart(int limit, int offset) {
        return super.getPart(limit, offset);
    }

    @Override
    public List<Order> getOrderedPart(int limit, int offset, String orderBy, String orderDir) {
        return super.getOrderedPart(limit, offset, orderBy, orderDir);
    }

    public List<Order> getOrderedPartByUserId(long userId, int limit, int offset) {
        try (Session session = getCurrentSession()) {
            return session.createQuery(
                    "FROM Order WHERE user.id = :userId ORDER BY createdAt DESC", Order.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
        }
    }

    public int countByUserId(long userId) {
        try (Session session = getCurrentSession()) {
            User user = session.get(User.class, userId);
            long count = user.getOrders().stream().count();
            return count > 0 ? (int) count : 0;
        }
    }

    public void cancelOrder(long id) {
        Transaction transaction = null;
        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                order.setStatus(3);
                order.setUpdatedAt(LocalDateTime.now());
                session.update(order);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void confirm(long id) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                order.setStatus(2); 
                order.setUpdatedAt(LocalDateTime.now());
                session.update(order);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(long id) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                order.setStatus(3);
                order.setUpdatedAt(LocalDateTime.now());
                session.update(order);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset(long id) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                order.setStatus(1); 
                order.setUpdatedAt(LocalDateTime.now());
                session.update(order);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int count() {
        try (Session session = getCurrentSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(id) FROM Order ", Long.class)
                .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
