package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvEmail, tvPhone, tvAddress;
    private ImageView avatar;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ view
        avatar = findViewById(R.id.avatar);
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        btnLogout = findViewById(R.id.btnLogout);

        // Lấy userId truyền từ LoginActivity (nếu có)
        int userId = getIntent().getIntExtra("userId", 1);

        // Gọi API bằng Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    tvName.setText("Tên: " + user.name.firstname + " " + user.name.lastname);
                    tvUsername.setText("Username: " + user.username);
                    tvEmail.setText("Email: " + user.email);
                    tvPhone.setText("SĐT: " + user.phone);
                    tvAddress.setText("Địa chỉ: " + user.address.number + " " + user.address.street + ", " + user.address.city);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Lỗi khi tải thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        // 👉 Xử lý Đăng xuất
        btnLogout.setOnClickListener(v -> {
            // Xoá SharedPreferences
            SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

            // Quay về màn hình Login và xoá lịch sử backstack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
