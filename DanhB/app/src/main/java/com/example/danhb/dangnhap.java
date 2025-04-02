package com.example.danhb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class dangnhap extends AppCompatActivity {

    private EditText edtemail;
    private EditText edtpassword;
    private Button btnDangNhap;
    private Button btnDangKi;
    private Button btnHienthi;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        mAuth = FirebaseAuth.getInstance();
        edtemail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPass);
        btnDangNhap = findViewById(R.id.btnDangnhap);
        btnDangKi = findViewById(R.id.btnDangki);


        btnDangNhap.setOnClickListener(v -> login());
        btnDangKi.setOnClickListener(v -> register());

    }

    private void onclickPushData() {
        String email = edtemail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void login() {
        String email = edtemail.getText().toString();
        String pass = edtpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(dangnhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(dangnhap.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(dangnhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register() {
        Intent i = new Intent(dangnhap.this, dangki.class);
        startActivity(i);
    }
}
