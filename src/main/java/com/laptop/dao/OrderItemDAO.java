package com.laptop.dao;

import com.laptop.dto.OrderItemRequest;
import com.laptop.dto.OrderRequest;
import com.laptop.models.Order;
import com.laptop.models.OrderItem;
import com.laptop.models.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class OrderItemDAO extends AbstractDAO<OrderItem> {

    public OrderItemDAO() {
        super(OrderItem.class);
    }

    public List<String> getProductNamesByOrderId(long orderId) {
        try (Session session = getCurrentSession()) {
            String hql = "SELECT p.name FROM Product p JOIN p.orderItems o WHERE o.order.id = :orderId";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("orderId", orderId);
            return query.list();
        }
    }

    public List<OrderItem> getByOrderId(long orderId) {
        try (Session session = getCurrentSession()) {
            String hql = "FROM OrderItem o WHERE o.order.id = :orderId";
            Query<OrderItem> query = session.createQuery(hql, OrderItem.class);
            query.setParameter("orderId", orderId);
            return query.list();
        }
    }

    public void bulkInsert(long orderId, List<OrderItemRequest> orderItems) {
        Transaction transaction = null;
        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, orderId);

            for (OrderItemRequest orderItem : orderItems) {
                long productId = orderItem.getProductId();
                Product product = session.get(Product.class, productId);
                long quantity = orderItem.getQuantity();
                double discount = orderItem.getDiscount();
                double price = orderItem.getPrice();

                OrderItem orderItemEntity = new OrderItem();
                orderItemEntity.setOrder(order);
                orderItemEntity.setProduct(product);
                orderItemEntity.setDiscount(discount);
                orderItemEntity.setPrice(price);
                orderItemEntity.setQuantity(quantity);
                session.save(orderItemEntity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
