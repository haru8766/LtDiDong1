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

    // Getter - Retrofit cần có các getter để ánh xạ JSON
    public String getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public double getOriginalPrice() { return originalPrice; }
    public double getDiscountPrice() { return discountPrice; }
    public String getDescription() { return description; }
}
