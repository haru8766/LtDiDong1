package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPhone, etPassword;
    Button btnRegister, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ánh xạ giao diện
        etUsername = findViewById(R.id.etUsername);
        etPhone    = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack     = findViewById(R.id.btnBack);

        // Xử lý nút Đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // ✅ Lưu thông tin người dùng vào SharedPreferences
                    SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("phone", phone);
                    editor.putString("password", password);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    // Quay lại màn hình đăng nhập và tự điền số điện thoại
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                }
            }
        });



        // Xử lý nút Trở lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), LoginActivity.class );
                startActivity(it);
                finish();
            }
        });

    }
}