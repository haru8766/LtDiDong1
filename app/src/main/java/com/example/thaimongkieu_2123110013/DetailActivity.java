package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView tvName, tvPriceNew, tvPriceOld, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        imgProduct = findViewById(R.id.imgProduct);
        tvName = findViewById(R.id.tvProductName);
        tvPriceNew = findViewById(R.id.tvPriceNew);
        tvPriceOld = findViewById(R.id.tvPriceOld);
        tvDescription = findViewById(R.id.tvDescription);

        // Nhận dữ liệu từ Intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            // Gán dữ liệu
            tvName.setText(product.getName());
            tvPriceNew.setText(String.format("%.0fđ", product.getDiscountPrice()));

            // Gạch ngang giá gốc
            String originalPrice = String.format("%.0fđ", product.getOriginalPrice());
            SpannableString spannable = new SpannableString(originalPrice);
            spannable.setSpan(new StrikethroughSpan(), 0, originalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPriceOld.setText(spannable);

            tvDescription.setText(product.getDescription());

            // Load ảnh từ URL
            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.p4)
                    .into(imgProduct);
        }

        // Xử lý nút quay lại
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(it);
            finish();
        });
    }
}
