package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.GridLayoutManager;


public class HomeActivity extends AppCompatActivity {

    ListView ListCategories;


    String tutorials[] = {
            "Tiểu thuyết",
            "Truyện ngắn",
            "Thơ",
            "Văn học Việt Nam",
            "Văn học nước ngoài",
            "Văn học kinh điển"
    };

    LinearLayout layoutCategoryRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        layoutCategoryRow = findViewById(R.id.layoutCategoryRow);

        for (String item : tutorials) {
            TextView textView = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.item_category_horizontal, null);
            textView.setText(item);
            layoutCategoryRow.addView(textView);
        }

        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.p4, "Hai số phận", "500.000đ", "350.000đ", "hai đường kẻ song song trên đường đua"));
        list.add(new Product(R.drawable.p5, "Không gia đình", "800.000đ", "600.000đ", "Những con người không gia đình cùng đồng hành"));
        list.add(new Product(R.drawable.p2, "Truyền kỳ mạn lục", "800.000đ", "600.000đ", "Cổ trang hay"));
        list.add(new Product(R.drawable.p1, "Thái tử song sinh", "800.000đ", "600.000đ", "Phiêu lưu"));

        // 💡 Gắn vào RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerProduct);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ProductAdapter adapter = new ProductAdapter(this, list);
        recyclerView.setAdapter(adapter);


        ImageView btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

    }

//         ListCategories = findViewById(R.id.list_1);
//         ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.item_home, tutorials);
//         ListCategories.setAdapter(arr);
    }
