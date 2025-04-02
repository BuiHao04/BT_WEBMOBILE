package com.example.reviewfirebaserealtimedatabase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity: ComponentActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnDangNhap: Button
    private lateinit var btnDangKi: Button
    private lateinit var btnHienthi: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dangnhap)
        mAuth = FirebaseAuth.getInstance()
        edtemail = findViewById(R.id.edtEmail)
        edtpassword = findViewById(R.id.edtPassword)
        btnDangNhap = findViewById(R.id.btnDangNhap)
        btnDangKi = findViewById(R.id.btnDangKi)
        btnHienthi = findViewById(R.id.btnHienthi)
        btnDangNhap.setOnClickListener {
            login()
        }
        btnDangKi.setOnClickListener {
            register()

        }
        btnHienthi.setOnClickListener {
            onclickPushData()
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
        }

    }

    private fun onclickPushData() {
        val email = edtemail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users")

        val userId = myRef.push().key  // Tạo một ID ngẫu nhiên
        val user = Users(email)

        if (userId != null) {
            myRef.child(userId).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ghi dữ liệu thành công!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi ghi dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun login() {
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
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Dang nhap thanh cong!!!", Toast.LENGTH_SHORT).show()
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Dang nhap that bai!!!", Toast.LENGTH_SHORT).show()
            }

        }
    }

        private fun register() {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }

    }
