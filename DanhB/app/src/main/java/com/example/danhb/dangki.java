package com.example.danhb;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;

public class dangki extends AppCompatActivity {
    private EditText edtemail;
    private EditText edtpassword;
    private EditText edtpassword2;
    private Button btnDangKi;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        mAuth = FirebaseAuth.getInstance();

        edtemail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPass);
        edtpassword2 = findViewById(R.id.edtPass2);
        btnDangKi = findViewById(R.id.btnDangki);

        btnDangKi.setOnClickListener(v -> register());
    }

    private void register() {
        String email = edtemail.getText().toString().trim();
        String pass = edtpassword.getText().toString().trim();
        String pass2 = edtpassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass2)) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(pass2)) {
            Toast.makeText(this, "Mật khẩu không khớp! Vui lòng nhập lại.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem email đã tồn tại hay chưa
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Kiểm tra xem email đã được đăng ký chưa
                List<String> signInMethods = task.getResult().getSignInMethods();
                if (signInMethods != null && !signInMethods.isEmpty()) {
                    // Email đã tồn tại
                    Toast.makeText(dangki.this, "Email đã được đăng ký. Vui lòng chọn email khác!", Toast.LENGTH_SHORT).show();
                } else {
                    // Email chưa được đăng ký, tiếp tục đăng ký
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(registerTask -> {
                        if (registerTask.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(verificationTask -> {
                                if (verificationTask.isSuccessful()) {
                                    Toast.makeText(this, "Tạo tài khoản thành công! Vui lòng kiểm tra email để xác nhận.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(dangki.this, dangnhap.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(this, "Gửi email xác nhận thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(dangki.this, "Lỗi khi kiểm tra email", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

