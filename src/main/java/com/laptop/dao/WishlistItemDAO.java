package com.laptop.dao;

import com.laptop.models.WishlistItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class WishlistItemDAO extends AbstractDAO<WishlistItem> {

    public WishlistItemDAO() {
        super(WishlistItem.class);
    }

    public List<WishlistItem> getByUserId(long userId) {
        try (Session session = getCurrentSession()) {
            return session.createQuery("from WishlistItem where user.id = :userId", WishlistItem.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public int countByUserIdAndProductId(long userId, long productId) {
        try (Session session = getCurrentSession()) {
            Long count = session.createQuery(
                    "select count(id) from WishlistItem where user.id = :userId and product.id = :productId", Long.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
