package com.example.reviewsqlites

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "qlnguoidung", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        // Tạo bảng "contacts" với hai cột: name (tên) và phone (số điện thoại)
        val createTable = """
            CREATE TABLE tbnguoidung (
                name TEXT NOT NULL PRIMARY KEY,
                phone TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tbnguoidung")
        onCreate(db)
    }

    // Thêm dữ liệu vào bảng tbnguoidung
    fun insertUser(name: String, phone: String): Boolean {
        val db = this.writableDatabase //writableDatabase: cho phép thao tác đọc và ghi với dữ liệu: insert, update, delete
        val values = ContentValues().apply {
            put("name", name)
            put("phone", phone)
        }
        val result = db.insert("tbnguoidung", null, values)
        db.close()
        return result != -1L // Trả về true nếu thêm thành công
    }

    // Cập nhật số điện thoại dựa trên tên
    fun updateUser(name: String, newPhone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("phone", newPhone)
        }
        val result = db.update("tbnguoidung", values, "name=?", arrayOf(name))
        db.close()
        return result > 0 // Trả về true nếu cập nhật thành công
    }

    // Xóa liên hệ dựa trên tên
    fun deleteUser(name: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete("tbnguoidung", "name=?", arrayOf(name))
        db.close()
        return result > 0 // Trả về true nếu xóa thành công
    }

    // Lấy tất cả dữ liệu từ bảng contacts
    fun getAllUsers(): List<String> {
        val db = readableDatabase
        val list = mutableListOf<String>()
        val cursor: Cursor = db.query("tbnguoidung", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val phone = cursor.getString(1)
            list.add("$name - $phone")
        }
        cursor.close()
        return list
    }
}