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

public class LoginActivity extends AppCompatActivity {

    EditText objPhone, objPass;
    Button btnNextPage, btnRegister;

    final String DEFAULT_PHONE = "0899468220";
    final String DEFAULT_PASS  = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ đúng cách
        objPhone     = findViewById(R.id.editTextPhone);
        objPass      = findViewById(R.id.editTextTextPassword);
        btnNextPage  = findViewById(R.id.btnLogin);
        btnRegister  = findViewById(R.id.btnRegister);

        // Nhận số điện thoại sau đăng ký (nếu có)
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("phone")) {
            String registeredPhone = intent.getStringExtra("phone");
            objPhone.setText(registeredPhone); // Tự động điền
        }

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPhone = objPhone.getText().toString().trim();
                String inputPass  = objPass.getText().toString().trim();

                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                String savedPhone = pref.getString("phone", "");
                String savedPass  = pref.getString("password", "");

                if ((inputPhone.equals(savedPhone) && inputPass.equals(savedPass)) ||
                        (inputPhone.equals(DEFAULT_PHONE) && inputPass.equals(DEFAULT_PASS))) {
                    Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Sai số điện thoại hoặc mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}