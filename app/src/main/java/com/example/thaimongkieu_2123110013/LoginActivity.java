package com.example.thaimongkieu_2123110013;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText objUsername, objPassword;
    Button btnLogin, btnRegister;

    private final String loginUrl = "https://fakestoreapi.com/users";

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

        objUsername = findViewById(R.id.editTextUsername); // username
        objPassword = findViewById(R.id.editTextTextPassword); // password
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // 👉 Tự động điền username nếu có từ RegisterActivity gửi sang
        String usernameFromRegister = getIntent().getStringExtra("username");
        if (usernameFromRegister != null) {
            objUsername.setText(usernameFromRegister);
        }

        btnLogin.setOnClickListener(v -> {
            String usernameInput = objUsername.getText().toString().trim();
            String passwordInput = objPassword.getText().toString().trim();

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                // 👉 Ưu tiên kiểm tra thông tin đã đăng ký local
                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                String savedUsername = pref.getString("username", "");
                String savedPassword = pref.getString("password", "");

                if (usernameInput.equals(savedUsername) && passwordInput.equals(savedPassword)) {
                    Toast.makeText(this, "Đăng nhập thành công (local)!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    // Không đúng → gọi API
                    loginWithApi(usernameInput, passwordInput);
                }
            }
        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginWithApi(String usernameInput, String passwordInput) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginUrl,
                response -> {
                    try {
                        JSONArray usersArray = new JSONArray(response);
                        boolean found = false;

                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject user = usersArray.getJSONObject(i);
                            String username = user.getString("username");
                            String password = user.getString("password");

                            if (username.equals(usernameInput) && password.equals(passwordInput)) {
                                Toast.makeText(this, "Đăng nhập thành công (API)!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Lỗi xử lý dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Không thể kết nối đến server!", Toast.LENGTH_SHORT).show();
                });

        queue.add(stringRequest);
    }
}
