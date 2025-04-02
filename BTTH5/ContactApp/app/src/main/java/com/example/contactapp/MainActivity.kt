package com.example.contactapp

import DatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactapp.ui.theme.ContactAppTheme

class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnShow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)
        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        btnAdd = findViewById(R.id.btnAdd)
        btnShow = findViewById(R.id.btnShow)

        btnAdd.setOnClickListener {
            val name = edtName.text.toString()
            val age = edtAge.text.toString().toInt()
            val isInserted = db.insertStudent(name, age)
            Toast.makeText(this, if (isInserted) "Thêm thành công!" else "Thêm thất bại!", Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            val cursor = db.getAllStudents()
            val data = StringBuilder()
            while (cursor.moveToNext()) {
                data.append("ID: ${cursor.getInt(0)}, Name: ${cursor.getString(1)}, Age: ${cursor.getInt(2)}\n")
            }
            Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show()
        }
    }
}



