package com.laptop.dao;

import com.laptop.models.ProductReview;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProductReviewDAO extends AbstractDAO<ProductReview> {

    public ProductReviewDAO() {
        super(ProductReview.class);
    }

    public long insert(ProductReview productReview) {
        try (Session session = getCurrentSession()) {
            session.save(productReview);
            return productReview.getId();
        }
    }

    public void update(ProductReview productReview) {
        try (Session session = getCurrentSession()) {
            session.update(productReview);
        }
    }

    public void delete(long id) {
        try (Session session = getCurrentSession()) {
            ProductReview productReview = session.load(ProductReview.class, id);
            session.delete(productReview);
        }
    }

    public Optional<ProductReview> getById(long id) {
        try (Session session = getCurrentSession()) {
            return Optional.ofNullable(session.get(ProductReview.class, id));
        }
    }

    public List<ProductReview> getAll() {
        try (Session session = getCurrentSession()) {
            return session.createQuery("FROM ProductReview", ProductReview.class).list();
        }
    }

    public List<ProductReview> getPart(int limit, int offset) {
        try (Session session = getCurrentSession()) {
            Query<ProductReview> query = session.createQuery("FROM ProductReview", ProductReview.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }

    public List<ProductReview> getOrderedPart(int limit, int offset, String orderBy, String orderDir) {
        try (Session session = getCurrentSession()) {
            String hql = "FROM ProductReview ORDER BY " + orderBy + " " + orderDir;
            Query<ProductReview> query = session.createQuery(hql, ProductReview.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }

    public List<ProductReview> getOrderedPartByProductId(int limit, int offset, String orderBy, String orderDir, long productId) {
        try (Session session = getCurrentSession()) {
            String hql = "FROM ProductReview pr JOIN FETCH pr.user WHERE pr.productId = :productId ORDER BY " + orderBy + " " + orderDir;
            Query<ProductReview> query = session.createQuery(hql, ProductReview.class);
            query.setParameter("productId", productId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }

    public int countByProductId(long productId) {
        try (Session session = getCurrentSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(pr.id) FROM ProductReview pr WHERE pr.product.id = :productId", Long.class);
            query.setParameter("productId", productId);
            return query.uniqueResult().intValue();
        }
    }

    public int sumRatingScoresByProductId(long productId) {
        try (Session session = getCurrentSession()) {
            Query<Long> query = session.createQuery("SELECT SUM(pr.ratingScore) FROM ProductReview pr WHERE pr.product.id = :productId", Long.class);
            query.setParameter("productId", productId);
            return query.uniqueResult().intValue();
        }
    }

    public int count() {
        try (Session session = getCurrentSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(pr.id) FROM ProductReview pr", Long.class);
            return query.uniqueResult().intValue();
        }
    }

    public void hide(long id) {
        try (Session session = getCurrentSession()) {
            ProductReview productReview = session.load(ProductReview.class, id);
            productReview.setIsShow(false);
            session.update(productReview);
        }
    }

    public void show(long id) {
        try (Session session = getCurrentSession()) {
            ProductReview productReview = session.load(ProductReview.class, id);
            productReview.setIsShow(true);
            session.update(productReview);
        }
    }
}
