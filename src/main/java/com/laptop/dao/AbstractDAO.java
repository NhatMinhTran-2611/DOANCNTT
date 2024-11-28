package com.laptop.dao;

import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T> implements DAO<T> {
    private final Class<T> entityClass;

    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public long insert(T t) {
        Transaction transaction = null;
        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();
            long id = (long) session.save(t);
            transaction.commit();
            return id;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void update(T t) {
        Transaction transaction = null;
        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();
            T t = session.load(entityClass, id);
            session.delete(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> getById(long id) {
        try (Session session = getCurrentSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = getCurrentSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        }
    }

    @Override
    public List<T> getPart(int limit, int offset) {
        try (Session session = getCurrentSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
        }
    }

    @Override
    public List<T> getOrderedPart(int limit, int offset, String orderBy, String orderDir) {
        try (Session session = getCurrentSession()) {
            return session.createQuery("from " + entityClass.getName() + " order by " + orderBy + " " + orderDir, entityClass)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
        }
    }
}