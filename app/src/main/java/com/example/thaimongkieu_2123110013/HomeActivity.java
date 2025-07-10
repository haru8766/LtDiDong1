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

import com.bumptech.glide.Glide;

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

    String tutorials[] = {
            "Tất cả",
            "Truyện tranh",
            "Trinh thám",
            "Kỳ ảo",
            "Văn học",
            "Công nghệ"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // ✅ Đúng thứ tự
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        EditText etSearch = findViewById(R.id.etSearch);
        ImageView btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearch.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchProductByName(keyword);
            } else {
                loadAllProducts();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutCategoryRow = findViewById(R.id.layoutCategoryRow);
        recyclerView = findViewById(R.id.recyclerProduct);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

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

        loadAllProducts();

        ImageView btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        ImageView avatar = findViewById(R.id.avatar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://686f0e0491e85fac429fa530.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        api.getUserInfo().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    // Hiển thị tên cuối (ví dụ: "Thái Mộng Kiều" -> "Kiều")
                    String[] parts = user.getName().split(" ");
                    String lastName = parts[parts.length - 1];
                    tvGreeting.setText("Hi, " + lastName + "!");

                    // Load avatar bằng Glide
                    Glide.with(HomeActivity.this)
                            .load(user.getAvatar())
                            .placeholder(R.drawable.avatar)
                            .into(avatar);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi tải user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
                    ProductAdapter adapter = new ProductAdapter(HomeActivity.this, response.body());
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
                    ProductAdapter adapter = new ProductAdapter(HomeActivity.this, response.body());
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
        if (keyword == null) keyword = ""; // đảm bảo không null
        String lowerKeyword = keyword.toLowerCase(); // dùng biến tạm

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
                        recyclerView.setAdapter(new ProductAdapter(HomeActivity.this, filtered));
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

