package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private static final int SHIPPING_FEE = 30000; // phí vận chuyển mặc định

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

        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        RecyclerView recyclerCheckout = findViewById(R.id.recyclerCheckout);
        TextView tvSubtotal = findViewById(R.id.tvSubtotal);
        TextView tvShippingFee = findViewById(R.id.tvShippingFee);
        TextView tvTotal = findViewById(R.id.tvTotal);

        // Lấy danh sách sản phẩm được chọn
        ArrayList<Product> selectedProducts = (ArrayList<Product>) getIntent().getSerializableExtra("selected_products");

        if (selectedProducts == null || selectedProducts.isEmpty()) {
            Toast.makeText(this, "Không có sản phẩm nào được chọn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị danh sách sản phẩm
        CheckoutProductAdapter adapter = new CheckoutProductAdapter(this, selectedProducts);
        recyclerCheckout.setLayoutManager(new LinearLayoutManager(this));
        recyclerCheckout.setAdapter(adapter);

        // Tính tổng tiền hàng (subtotal)
        double subtotal = 0;
        for (Product p : selectedProducts) {
            subtotal += p.getDiscountPrice() * p.getQuantity(); // quantity đã xử lý khi gộp
        }

        // Gán giá trị vào textview
        tvSubtotal.setText(String.format("%,.0fđ", subtotal));
        tvShippingFee.setText(String.format("%,dđ", SHIPPING_FEE));
        tvTotal.setText(String.format("%,.0fđ", subtotal + SHIPPING_FEE));


        // Đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty()) {
                edtName.setError("Vui lòng nhập họ và tên");
                edtName.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                edtPhone.setError("Vui lòng nhập số điện thoại");
                edtPhone.requestFocus();
                return;
            }

            if (!phone.matches("\\d{9,11}")) {
                edtPhone.setError("Số điện thoại không hợp lệ");
                edtPhone.requestFocus();
                return;
            }

            if (address.isEmpty()) {
                edtAddress.setError("Vui lòng nhập địa chỉ");
                edtAddress.requestFocus();
                return;
            }

            // Nếu hợp lệ hết -> đặt hàng
            Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            CartManager.clearCart(); // Xoá giỏ hàng sau khi đặt hàng
            startActivity(new Intent(CheckoutActivity.this, HomeActivity.class));
            finish();
        });

    }
}
