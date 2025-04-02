package com.example.reviewsqlites

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var edtname: EditText
    private lateinit var edtphone: EditText
    private lateinit var btnThem: Button
    private lateinit var btnSua: Button
    private lateinit var btnXoa: Button
    private lateinit var btnHienthi: Button
    private lateinit var lstView: ListView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var myadapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //mylist = ArrayList()
        edtname = findViewById(R.id.edtName)
        edtphone = findViewById(R.id.edtPhone)
        btnHienthi = findViewById(R.id.btnHienthi)
        btnThem = findViewById(R.id.btnThem)
        btnSua = findViewById(R.id.btnSua)
        btnXoa = findViewById(R.id.BtnXoa)
        lstView = findViewById(R.id.lstView)

        //khoi tao database helper
        databaseHelper = DatabaseHelper(this)
        //gan adapter cho listview
        myadapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, databaseHelper.getAllUsers())
        lstView.adapter = myadapter


        myadapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList())
        lstView.setAdapter(myadapter)

        //xu ly su kien them
        btnThem.setOnClickListener(View.OnClickListener() {
            val name = edtname.getText().toString();
            val phone = edtphone.getText().toString();
            if (name.isNotEmpty() && phone.isNotEmpty()){
                if (databaseHelper.insertUser(name, phone)) {
                    Toast.makeText(this@MainActivity, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    loaddata()
                } else {
                    Toast.makeText(this@MainActivity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@MainActivity, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }

        })
        //xu ly su kien xoa
        btnXoa.setOnClickListener(View.OnClickListener() {
            val name = edtname.getText().toString();
            if (name.isNotEmpty()){
                if (databaseHelper.deleteUser(name)) {
                    Toast.makeText(this@MainActivity, "Xóa thành công", Toast.LENGTH_SHORT).show()
                    loaddata()
                } else {
                    Toast.makeText(this@MainActivity, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@MainActivity, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        })

       btnSua.setOnClickListener(View.OnClickListener() {
           val name = edtname.getText().toString();
           val phone = edtphone.getText().toString();
           if (name.isNotEmpty() && phone.isNotEmpty()) {
               if (databaseHelper.updateUser(name, phone)) {
                   Toast.makeText(this@MainActivity, "Sửa thành công", Toast.LENGTH_SHORT).show()
                   loaddata()
               } else {
                   Toast.makeText(this@MainActivity, "Sửa thất bại", Toast.LENGTH_SHORT).show()
               }
           } else{
                Toast.makeText(this@MainActivity, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
           }

        })
        btnHienthi.setOnClickListener(View.OnClickListener(){
            loaddata()
        })
    }
    private fun loaddata(){
        val datalist = databaseHelper.getAllUsers()
        myadapter.clear()
        myadapter.addAll(datalist)
        myadapter.notifyDataSetChanged()
    }
}
