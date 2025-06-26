package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

        // Gán nút back
        ImageButton btnBack = findViewById(R.id.btnBack1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình chính (có thể là MainActivity hoặc HomeActivity thay vì LoginActivity)
                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(it);
                finish();
            }
        });

        RecyclerView recyclerCart = findViewById(R.id.recyclerCart);
        TextView tvTotal = findViewById(R.id.tvTotal);

        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.p2, "Thái tử song sinh", "300.000đ", "250.000đ", "văn học phương tây"));
        list.add(new Product(R.drawable.p1, "Truyền kỳ mạn lục", "400.000đ", "300.000đ", "Cổ trang"));

        CartAdapter adapter = new CartAdapter(list);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(adapter);

// Gắn listener để cập nhật tổng giá
        adapter.setOnItemCheckedChangeListener(total -> {
            tvTotal.setText("Tổng: " + total + "đ");
        });

        Button btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });




    }
}