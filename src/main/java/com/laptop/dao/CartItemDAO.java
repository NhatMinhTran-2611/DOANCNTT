package com.laptop.dao;

import com.laptop.models.CartItem;
import com.laptop.utils.HibernateUtil;
import org.hibernate.Session;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public class CartItemDAO extends AbstractDAO<CartItem> {

    public CartItemDAO() {
        super(CartItem.class);
    }

    // Implement methods specific to CartItemDAO

    public List<CartItem> getByCartId(long cartId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CartItem where cart.id = :cartId order by createdAt desc";
            return session.createQuery(hql, CartItem.class)
                    .setParameter("cartId", cartId)
                    .list();
        }
    }

    public Optional<CartItem> getByCartIdAndProductId(long cartId, long productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CartItem where cart.id = :cartId and product.id = :productId";
            return session.createQuery(hql, CartItem.class)
                    .setParameter("cartId", cartId)
                    .setParameter("productId", productId)
                    .uniqueResultOptional();
        }
    }

    public int sumQuantityByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long sum = session.createQuery("select sum(ci.quantity) from CartItem ci join ci.cart c where c.user.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
            return sum != null ? sum.intValue() : 0;
        }
    }

    // ...existing code...
}
