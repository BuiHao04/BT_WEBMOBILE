package com.example.sharedpreference

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharedpreference.ui.theme.SharedPreferenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //anh xa cac doi tuong trong layout
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnHienThi = findViewById<Button>(R.id.btnHienThi)
        val btnLuu = findViewById<Button>(R.id.btnLuu)
        val btnXoa = findViewById<Button>(R.id.btnXoa)

        val preferenceHelper = PreferenceHelper(this)

        //Su kien Hien thi khi nhan nut Hien thi
        btnHienThi.setOnClickListener {
            val name= preferenceHelper.getUserName()
            val password = preferenceHelper.getPassword()
            // In ra logcat để kiểm tra
            Log.d("SharedPreferences", "Name: $name, Password: $password")
            edtName.setText(name)
            edtPassword.setText(password)
        }

        //Su kien Luu khi nhan nut Luu
        btnLuu.setOnClickListener {
            val name = edtName.text.toString()
            val password = edtPassword.text.toString()
            Log.d("SharedPreferences", "Lưu: Name = $name, Password = $password")
            preferenceHelper.saveUser(name, password)
            edtName.setText("")
            edtPassword.setText("")
        }


        //Su kien Xoa khi nhan nut Xoa
        btnXoa.setOnClickListener{
            Log.d("SharedPreferences", "Xóa dữ liệu")
            preferenceHelper.clearData()
            edtName.setText("")
            edtPassword.setText("")
        }
        }
    }

