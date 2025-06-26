package com.example.thaimongkieu_2123110013;

//    public class Product {
//        public int imageResId;
//        public String name;
//        public String oldPrice;
//        public String newPrice;
//        public String description;
//
//        public Product(int imageResId, String name, String oldPrice, String newPrice, String description) {
//            this.imageResId = imageResId;
//            this.name = name;
//            this.oldPrice = oldPrice;
//            this.newPrice = newPrice;
//            this.description = description;
//        }
//    }

import java.io.Serializable;

public class Product implements Serializable {
    public int imageResId;
    public String name, oldPrice, newPrice, description;

    public Product(int imageResId, String name, String oldPrice, String newPrice, String description) {
        this.imageResId = imageResId;
        this.name = name;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.description = description;
    }
}

