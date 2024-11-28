package com.laptop.dao;

import com.laptop.models.Category;
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

public class CategoryDAO extends AbstractDAO<Category> {

    public CategoryDAO() {
        super(Category.class);
    }

    public Optional<Category> getByProductId(long productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select c from Category c join c.products p where p.id = :productId";
            return session.createQuery(hql, Category.class)
                    .setParameter("productId", productId)
                    .uniqueResultOptional();
        }
    }

    public int count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(id) from Category", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    // ...existing code...
}
