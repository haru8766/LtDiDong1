package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack1);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, HomeActivity.class));
            finish();
        });

        RecyclerView recyclerCart = findViewById(R.id.recyclerCart);
        TextView tvEmptyCart = findViewById(R.id.tvEmptyCart); // THÊM DÒNG NÀY
        TextView tvTotal = findViewById(R.id.tvTotal);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        List<Product> cartList = CartManager.getCart();

        // Kiểm tra nếu giỏ hàng trống
        if (cartList == null || cartList.isEmpty()) {
            recyclerCart.setVisibility(View.GONE);
            tvEmptyCart.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(false); // không cho thanh toán
            tvTotal.setText("Tổng: 0đ");
            return;
        } else {
            recyclerCart.setVisibility(View.VISIBLE);
            tvEmptyCart.setVisibility(View.GONE);
            btnCheckout.setEnabled(true);
        }

        CartAdapter adapter = new CartAdapter(cartList);
        adapter.setOnCartChangedListener(new CartAdapter.OnCartChangedListener() {
            @Override
            public void onCartUpdated(double total) {
                tvTotal.setText("Tổng: " + formatCurrency(total));
            }

            @Override
            public void onItemRemoved(Product removedItem) {
                CartManager.removeProduct(removedItem);
            }
        });

        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(adapter);

        // Tổng tiền ban đầu (nếu cần tính từ các sản phẩm được chọn)
        double initialTotal = 0;
        for (Product p : cartList) {
            if (p.isSelected()) {
                initialTotal += p.getDiscountPrice() * p.getQuantity();
            }
        }
        tvTotal.setText("Tổng: " + formatCurrency(initialTotal));

        btnCheckout.setOnClickListener(v -> {
            ArrayList<Product> selectedItems = new ArrayList<>();
            for (Product p : cartList) {
                if (p.isSelected()) {
                    selectedItems.add(p);
                }
            }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("selected_products", selectedItems);
            startActivity(intent);
        });
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(amount);
    }
}
