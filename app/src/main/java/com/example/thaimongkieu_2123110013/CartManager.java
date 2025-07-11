package com.example.thaimongkieu_2123110013;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<Product> cartList = new ArrayList<>();

    // Thêm sản phẩm vào giỏ hàng (có gộp số lượng nếu trùng)
    public static void addProduct(Product newProduct) {
        // Nếu sản phẩm không có quantity hoặc <= 0, set lại là 1
        if (newProduct.getQuantity() <= 0) {
            newProduct.setQuantity(1);
        }

        for (Product p : cartList) {
            if (p.getId().equals(newProduct.getId())) {
                // Nếu sản phẩm đã tồn tại, gộp số lượng
                p.setQuantity(p.getQuantity() + newProduct.getQuantity());
                return;
            }
        }

        // Nếu chưa có, thêm mới vào danh sách
        cartList.add(newProduct);
    }

    // Trả danh sách giỏ hàng
    public static List<Product> getCart() {
        return new ArrayList<>(cartList);
    }

    // Xoá một sản phẩm cụ thể khỏi giỏ
    public static void removeProduct(Product product) {
        cartList.remove(product);
    }

    // Xoá toàn bộ giỏ hàng
    public static void clearCart() {
        cartList.clear();
    }

    // Tính tổng tiền các sản phẩm được chọn
    public static double getTotal() {
        double total = 0;
        for (Product p : cartList) {
            if (p.isSelected()) {
                total += p.getDiscountPrice() * p.getQuantity();
            }
        }
        return total;
    }
}
