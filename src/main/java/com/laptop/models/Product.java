package com.laptop.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double price;
    private double discount;
    private int quantity;
    private int totalBuy;
    private String brand;
    private String description;
    private String imageName;
    private int shop;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String RAM;
    private String CPU;
    private String VGA;
    private String OS;
    private String Battery;
    private String SSD;
    private float weight;
    private float screenSize;
    private String color;

    public Product(String name, double price, double discount, int quantity, int totalBuy, String brand, String description, String imageName, int shop, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime startsAt, LocalDateTime endsAt, String RAM, String CPU, String VGA, String OS, String battery, String SSD, float weight, float screenSize, String color, Category category, List<ProductReview> productReviews, List<OrderItem> orderItems, List<CartItem> cartItems, List<WishlistItem> wishlistItems) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.totalBuy = totalBuy;
        this.brand = brand;
        this.description = description;
        this.imageName = imageName;
        this.shop = shop;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.RAM = RAM;
        this.CPU = CPU;
        this.VGA = VGA;
        this.OS = OS;
        this.Battery = battery;
        this.SSD = SSD;
        this.weight = weight;
        this.screenSize = screenSize;
        this.color = color;
        this.category = category;
        this.productReviews = productReviews;
        this.orderItems = orderItems;
        this.cartItems = cartItems;
        this.wishlistItems = wishlistItems;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getVGA() {
        return VGA;
    }

    public void setVGA(String VGA) {
        this.VGA = VGA;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getSSD() {
        return SSD;
    }

    public void setSSD(String SSD) {
        this.SSD = SSD;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    private List<WishlistItem> wishlistItems;

    public Product() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(int totalBuy) {
        this.totalBuy = totalBuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
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

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("discount=" + discount)
                .add("quantity=" + quantity)
                .add("totalBuy=" + totalBuy)
                .add("brand='" + brand + "'")
                .add("description='" + description + "'")
                .add("imageName='" + imageName + "'")
                .add("shop=" + shop)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .add("startsAt=" + startsAt)
                .add("endsAt=" + endsAt)
                .add("RAM='" + RAM + "'")
                .add("CPU='" + CPU + "'")
                .add("VGA='" + VGA + "'")
                .add("OS='" + OS + "'")
                .add("Battery='" + Battery + "'")
                .add("SSD='" + SSD + "'")
                .add("weight=" + weight)
                .add("screenSize=" + screenSize)
                .add("color='" + color + "'")
                .add("category=" + (category != null ? category.getId() : null))  // Assume Category has an ID for simplicity
                .add("productReviews.size=" + (productReviews != null ? productReviews.size() : 0))
                .add("orderItems.size=" + (orderItems != null ? orderItems.size() : 0))
                .add("cartItems.size=" + (cartItems != null ? cartItems.size() : 0))
                .add("wishlistItems.size=" + (wishlistItems != null ? wishlistItems.size() : 0))
                .toString();
    }

}
