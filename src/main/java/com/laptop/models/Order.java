    package com.laptop.models;

    import jakarta.persistence.*;

    import javax.swing.*;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.StringJoiner;

    @Entity
    @Table(name = "orders")
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private int status;
        private int deliveryMethod;
        private double deliveryPrice;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private double totalPrice;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
        private List<OrderItem> orderItems;

        public Order() {}

        public Order(long id,
                     int status,
                     int deliveryMethod,
                     double deliveryPrice,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
            this.id = id;
            this.status = status;
            this.deliveryMethod = deliveryMethod;
            this.deliveryPrice = deliveryPrice;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(int deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public double getDeliveryPrice() {
            return deliveryPrice;
        }

        public void setDeliveryPrice(double deliveryPrice) {
            this.deliveryPrice = deliveryPrice;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                    .add("id=" + id)
                    .add("status=" + status)
                    .add("deliveryMethod=" + deliveryMethod)
                    .add("deliveryPrice=" + deliveryPrice)
                    .add("createdAt=" + createdAt)
                    .add("updatedAt=" + updatedAt)
                    .add("user=" + user)
                    .add("orderItems=" + orderItems)
                    .add("totalPrice=" + totalPrice)
                    .toString();
        }
    }
