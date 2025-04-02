package com.example.reviewfirebaserealtimedatabase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity: ComponentActivity() {
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnDangKi: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dangki)

        mAuth = FirebaseAuth.getInstance()

        edtemail = findViewById(R.id.edtEmail)
        edtpassword = findViewById(R.id.edtPassword)
        btnDangKi = findViewById(R.id.btnDangKi)
        btnDangKi.setOnClickListener {
            register()

        }

    }

    private fun register() {
        val email = edtemail.getText().toString()
        val pass = edtpassword.getText().toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "vui long nhap email!!!", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "vui long nhap password!!!", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Tao tai khoan thanh cong!", Toast.LENGTH_SHORT).show()
                val user = mAuth.currentUser
                user?.let {
                    saveUserToDatabase(it.uid, email) // Lưu vào Firebase Realtime Database
                    // Thực hiện các thao tác khác sau khi đăng ký thành công

                }
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this@RegisterActivity, "Dang ki that bai!!!", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun saveUserToDatabase(uid: String, email: String) {
        val database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Users(email)  // Đảm bảo class Users có constructor mặc định

        database.child(uid).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Lưu thông tin người dùng thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Lỗi khi lưu dữ liệu!", Toast.LENGTH_SHORT).show()
            }
        }
    }




}