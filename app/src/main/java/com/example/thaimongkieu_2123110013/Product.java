package com.example.thaimongkieu_2123110013;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String imageUrl;
    private String name;
    private String author;
    private String category;
    private double originalPrice;
    private double discountPrice;
    private String description;

    private int quantity = 1;
    private boolean selected = true;

    // Constructor đầy đủ
    public Product(String id, String imageUrl, String name, String author, String category,
                   double originalPrice, double discountPrice, String description,
                   int quantity, boolean selected) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.author = author;
        this.category = category;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.description = description;
        this.quantity = quantity;
        this.selected = selected;
    }

    // Getters
    public String getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public double getOriginalPrice() { return originalPrice; }
    public double getDiscountPrice() { return discountPrice; }
    public String getDescription() { return description; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
