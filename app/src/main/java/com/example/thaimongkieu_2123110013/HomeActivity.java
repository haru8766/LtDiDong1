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
import android.widget.ListView;

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

ListCategories = findViewById(R.id.list);
        ArrayAdapter<String> arr;

        arr = new ArrayAdapter<>(this,R.layout.item_home, tutorials);

        ListCategories.setAdapter(arr);
    }
}