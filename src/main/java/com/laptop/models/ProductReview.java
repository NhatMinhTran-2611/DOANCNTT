package com.laptop.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int ratingScore;
    private String content;
    private boolean isShow;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductReview() {}

    public ProductReview(long id,
                         int ratingScore,
                         String content,
                         boolean isShow,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         User user,
                         Product product) {
        this.id = id;
        this.ratingScore = ratingScore;
        this.content = content;
        this.isShow = isShow;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductReview.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("ratingScore=" + ratingScore)
                .add("content='" + content + "'")
                .add("isShow=" + isShow)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .add("user=" + user)
                .add("product=" + product)
                .toString();
    }
}
