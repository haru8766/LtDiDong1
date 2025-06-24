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

import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    String[] productList = {
            "Sản phẩm 1", "Sản phẩm 2", "Sản phẩm 3"
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


        LinearLayout layoutProductRow;

        layoutProductRow = findViewById(R.id.layoutProductRow);

        for (int i = 0; i < productList.length; i++) {
            View productItem = LayoutInflater.from(this).inflate(R.layout.item_product, null);

            ImageView img = productItem.findViewById(R.id.imgProduct);
            TextView title = productItem.findViewById(R.id.tvProductTitle);
            TextView price = productItem.findViewById(R.id.tvPrice);
            TextView oldPrice = productItem.findViewById(R.id.tvOldPrice);
            TextView btnDetail = productItem.findViewById(R.id.btnDetail);

            // Set dữ liệu
            img.setImageResource(R.drawable.slider);
            title.setText("Sản phẩm " + (i + 1));
            price.setText("100.000đ");
            oldPrice.setText("150.000đ");

            btnDetail.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                // Nếu muốn truyền dữ liệu:
                intent.putExtra("productTitle", title.getText().toString());
                startActivity(intent);
            });

            layoutProductRow.addView(productItem);
        }




//         ListCategories = findViewById(R.id.list_1);
//         ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.item_home, tutorials);
//         ListCategories.setAdapter(arr);
    }}
