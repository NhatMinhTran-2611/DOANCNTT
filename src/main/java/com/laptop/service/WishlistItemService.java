package com.laptop.service;

import com.laptop.models.Order;
import com.laptop.models.Product;
import com.laptop.models.User;
import com.laptop.models.WishlistItem;
import com.laptop.dao.WishlistItemDAO;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class WishlistItemService extends WishlistItemDAO  {
    public WishlistItemService() {
    }

    public long insert(long productId, long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            WishlistItem wishlistItem = new WishlistItem();
            User user = session.get(User.class, userId);
            Product product = session.get(Product.class, productId);
            wishlistItem.setProduct(product);
            wishlistItem.setUser(user);
            session.save(wishlistItem);
            transaction.commit();
            return wishlistItem.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            return 0L;
        }

    }
}
