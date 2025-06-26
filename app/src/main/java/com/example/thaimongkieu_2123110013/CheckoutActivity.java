package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtName = findViewById(R.id.edtName);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtAddress = findViewById(R.id.edtAddress);
        Button btnConfirm = findViewById(R.id.btnConfirmAddress);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        RecyclerView recyclerCheckout = findViewById(R.id.recyclerCheckout);

        // 1. Dữ liệu truyền từ giỏ hàng
        ArrayList<Product> selectedProducts = (ArrayList<Product>) getIntent().getSerializableExtra("selected_products");

        // 2. Nếu không có dữ liệu truyền vào -> gắn sản phẩm cứng
        if (selectedProducts == null) {
            selectedProducts = new ArrayList<>();
            selectedProducts.add(new Product(R.drawable.p1, "Áo hoodie", "300.000đ", "250.000đ", "Áo ấm mùa đông"));
        }

        // 3. Hiển thị log kiểm tra
        for (Product p : selectedProducts) {
            Log.d("CHECKOUT", "Tên SP: " + p.name);
        }

        // 4. Gắn dữ liệu vào RecyclerView
        CheckoutProductAdapter adapter = new CheckoutProductAdapter(this, selectedProducts);
        recyclerCheckout.setLayoutManager(new LinearLayoutManager(this));
        recyclerCheckout.setAdapter(adapter);

        // 5. Xác nhận địa chỉ
        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Địa chỉ đã được xác nhận!", Toast.LENGTH_SHORT).show();
            }
        });

        // 6. Đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
