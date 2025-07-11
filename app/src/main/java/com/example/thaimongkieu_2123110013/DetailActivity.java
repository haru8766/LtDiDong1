package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgProduct;
    private TextView tvName, tvPriceNew, tvPriceOld, tvDescription;
    private RecyclerView recyclerRelated;
    private Product product;

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

        // Ánh xạ View
        imgProduct = findViewById(R.id.imgProduct);
        tvName = findViewById(R.id.tvProductName);
        tvPriceNew = findViewById(R.id.tvPriceNew);
        tvPriceOld = findViewById(R.id.tvPriceOld);
        tvDescription = findViewById(R.id.tvDescription);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBuyNow = findViewById(R.id.btnBuyNow);
        ImageButton btnBack = findViewById(R.id.btnBack);
        recyclerRelated = findViewById(R.id.recyclerRelated);

        recyclerRelated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Nhận dữ liệu sản phẩm
        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            // Gán dữ liệu sản phẩm
            tvName.setText(product.getName());
            tvPriceNew.setText(String.format("%.0fđ", product.getDiscountPrice()));

            String originalPrice = String.format("%.0fđ", product.getOriginalPrice());
            SpannableString spannable = new SpannableString(originalPrice);
            spannable.setSpan(new StrikethroughSpan(), 0, originalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPriceOld.setText(spannable);

            tvDescription.setText(product.getDescription());

            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.p4)
                    .into(imgProduct);

            // Tải sản phẩm liên quan
            loadRelatedProducts(product.getId(), product.getCategory());
        }

        // Nút thêm vào giỏ
        btnAddToCart.setOnClickListener(v -> {
            CartManager.addProduct(product);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // Nút mua ngay
        btnBuyNow.setOnClickListener(v -> {
            product.setQuantity(1);
            product.setSelected(true);

            ArrayList<Product> checkoutList = new ArrayList<>();
            checkoutList.add(product);

            Intent intent = new Intent(DetailActivity.this, CheckoutActivity.class);
            intent.putExtra("selected_products", checkoutList);
            startActivity(intent);
        });

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(it);
            finish();
        });
    }

    private void loadRelatedProducts(String productId, String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://686f0e0491e85fac429fa530.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> related = new ArrayList<>();
                    for (Product p : response.body()) {
                        if (!p.getId().equals(productId) && p.getCategory().equals(category)) {
                            related.add(p);
                        }
                    }

                    ProductAdapter adapter = new ProductAdapter(DetailActivity.this, related, p -> {
                        CartManager.addProduct(p);
                        Toast.makeText(DetailActivity.this, "Đã thêm \"" + p.getName() + "\" vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    });

                    recyclerRelated.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Không tải được sản phẩm liên quan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
