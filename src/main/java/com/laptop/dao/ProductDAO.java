package com.laptop.dao;

import com.laptop.models.Category;
import com.laptop.models.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
// ...other necessary Hibernate imports...

public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO() {
        super(Product.class);
    }

    @Override
    public long insert(Product product) {
        return super.insert(product);
    }


    @Override
    public void update(Product product) {
        super.update(product);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public Optional<Product> getById(long id) {
        return super.getById(id);
    }

    @Override
    public List<Product> getAll() {
        return super.getAll();
    }

    @Override
    public List<Product> getPart(int limit, int offset) {
        return super.getPart(limit, offset);
    }

    @Override
    public List<Product> getOrderedPart(int limit, int offset, String orderBy, String orderDir) {
        return super.getOrderedPart(limit, offset, orderBy, orderDir);
    }

    public List<Product> getOrderedPartByCategoryId(int limit, int offset, String orderBy, String orderDir, long categoryId) {
        try (Session session = getCurrentSession()) {
            // Create the JPQL query string with dynamic sorting
            String queryStr = "SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p." + orderBy + " " + orderDir;

            // Create the query
            Query<Product> query = session.createQuery(queryStr, Product.class);

            // Set the parameters for categoryId, limit, and offset
            query.setParameter("categoryId", categoryId);
            query.setFirstResult(offset);  // Set the offset for pagination
            query.setMaxResults(limit);    // Set the limit for pagination

            // Execute the query and return the result list
            return query.getResultList();

        }
    }

    public int countByCategoryId(long categoryId) {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT COUNT(p.id) FROM Product p JOIN p.category c WHERE c.id = :categoryId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("categoryId", categoryId);
            return query.uniqueResult().intValue();
        }
    }

    public List<Product> getRandomPartByCategoryId(int limit, int offset, long categoryId) {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT p FROM Product p JOIN p.category c WHERE c.id = :categoryId ORDER BY rand()";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("categoryId", categoryId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }



    public int countByCategoryIdAndFilters(long categoryId, String filters) {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT COUNT(p.id) FROM Product p JOIN p.category c WHERE c.id = :categoryId " + filters;
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("categoryId", categoryId);
            return query.uniqueResult().intValue();
        }
    }

    public List<Product> getOrderedPartByCategoryIdAndFilters(
            int limit, int offset, String orderBy, String orderDir,
            long categoryId, String filters) {

        try (Session session = getCurrentSession()) {

            // Start with the basic query filtering by categoryId
            StringBuilder hql = new StringBuilder("FROM Product p WHERE p.category.id = :categoryId");

            // Add additional filters if provided (for example price range, name, etc.)
            if (filters != null && !filters.isEmpty()) {
                hql.append(" AND ").append(filters);
            }

            // Add sorting based on orderBy and orderDir
            if (orderBy != null && !orderBy.isEmpty()) {
                hql.append(" ORDER BY p.").append(orderBy)
                        .append(" ").append(orderDir != null ? orderDir : "ASC"); // Default to "ASC" if orderDir is null
            }

            // Create the query
            Query<Product> query = session.createQuery(hql.toString(), Product.class);

            // Set parameters
            query.setParameter("categoryId", categoryId);

            // Set pagination parameters (limit and offset)
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            // Execute and return the result list
            return query.list();
        }
    }

    public void insertProductCategory(long productId, long categoryId) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Product pc = session.find(Product.class, productId);
            Category c = session.find(Category.class, categoryId);
            pc.setCategory(c);
            session.save(pc);
            transaction.commit();
        }
    }

    public void updateProductCategory(long productId, long categoryId) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Product pc = session.find(Product.class, productId);
            Category c = session.find(Category.class, categoryId);
            if (pc != null) {
                pc.setCategory(c);
                session.update(pc);
            }
            transaction.commit();
        }
    }

    public void deleteProductCategory(long productId) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Product pc = session.find(Product.class, productId);
            if (pc != null) {
                pc.setCategory(null);
            }
            transaction.commit();
        }
    }

    public List<Product> getByQuery(String queryStr, int limit, int offset) {
        try (Session session = getCurrentSession()) {
            String hql = "FROM Product p WHERE p.name LIKE :query";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("query", "%" + queryStr + "%");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }

    public int countByQuery(String queryStr) {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT COUNT(p.id) FROM Product p WHERE p.name LIKE :query";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("query", "%" + queryStr + "%");
            return query.uniqueResult().intValue();
        }
    }

    public List<String> getDistinctRAMValues() {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT DISTINCT p.RAM FROM Product p WHERE p.RAM IS NOT NULL ORDER BY p.RAM";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        }
    }

    public List<String> getDistinctVGAValues() {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT DISTINCT p.VGA FROM Product p WHERE p.VGA IS NOT NULL ORDER BY p.VGA";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        }
    }

    public List<String> getDistinctCPUsValues() {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT DISTINCT p.CPU FROM Product p WHERE p.CPU IS NOT NULL ORDER BY p.CPU";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        }
    }
}
