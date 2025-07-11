package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout layoutCategoryRow;
    private TextView tvCartCount;
    private int cartCount = 0;

    String tutorials[] = {
            "Tất cả", "Truyện tranh", "Trinh thám", "Kỳ ảo", "Văn học", "Công nghệ"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        tvCartCount = findViewById(R.id.tvCartCount);
        ImageView btnCart = findViewById(R.id.btnCart);
        EditText etSearch = findViewById(R.id.etSearch);
        ImageView btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerProduct);
        layoutCategoryRow = findViewById(R.id.layoutCategoryRow);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (String item : tutorials) {
            TextView textView = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.item_category_horizontal, null);
            textView.setText(item);

            textView.setOnClickListener(v -> {
                highlightSelectedCategory(textView);
                if (item.equals("Tất cả")) {
                    loadAllProducts();
                } else {
                    loadProductsByCategory(item);
                }
            });

            layoutCategoryRow.addView(textView);
        }

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearch.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchProductByName(keyword);
            } else {
                loadAllProducts();
            }
        });

        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView avatar = findViewById(R.id.avatar);
        avatar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        loadAllProducts();
    }

    private void updateCartCount() {
        if (cartCount > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(cartCount));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }
    }

    private void highlightSelectedCategory(TextView selected) {
        int count = layoutCategoryRow.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = layoutCategoryRow.getChildAt(i);
            if (view instanceof TextView) {
                view.setBackgroundResource(R.drawable.bg_category_item);
            }
        }
        selected.setBackgroundResource(R.drawable.bg_category_selected);
    }

    private void loadAllProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://686f0e0491e85fac429fa530.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductAdapter adapter = new ProductAdapter(HomeActivity.this, response.body(), product -> {
                        cartCount++;
                        updateCartCount();
                        Toast.makeText(HomeActivity.this, "Đã thêm \"" + product.getName() + "\" vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadProductsByCategory(String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://686f0e0491e85fac429fa530.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        api.getProductsByCategory(category).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductAdapter adapter = new ProductAdapter(HomeActivity.this, response.body(), product -> {
                        cartCount++;
                        updateCartCount();
                        Toast.makeText(HomeActivity.this, "Đã thêm \"" + product.getName() + "\" vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Không có sản phẩm phù hợp", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi khi tải danh sách: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchProductByName(String keyword) {
        String lowerKeyword = keyword.toLowerCase();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://686f0e0491e85fac429fa530.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> allProducts = response.body();
                    List<Product> filtered = new ArrayList<>();

                    for (Product p : allProducts) {
                        if (p.getName() != null && p.getName().toLowerCase().contains(lowerKeyword)) {
                            filtered.add(p);
                        }
                    }

                    if (!filtered.isEmpty()) {
                        recyclerView.setAdapter(new ProductAdapter(HomeActivity.this, filtered, product -> {
                            cartCount++;
                            updateCartCount();
                            Toast.makeText(HomeActivity.this, "Đã thêm \"" + product.getName() + "\" vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }));
                    } else {
                        Toast.makeText(HomeActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
