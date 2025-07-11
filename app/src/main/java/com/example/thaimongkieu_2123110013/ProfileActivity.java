package com.example.thaimongkieu_2123110013;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvEmail, tvPhone, tvAddress;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ
        avatar = findViewById(R.id.avatar);
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);

        // Lấy userId từ login truyền qua (ví dụ là 1)
        int userId = getIntent().getIntExtra("userId", 1);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null) {
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
    }
}
