import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "students.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE Students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Students")
        onCreate(db)
    }

    fun insertStudent(name: String, age: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("age", age)
        }
        val result = db.insert("Students", null, values)
        return result != -1L
    }

    fun getAllStudents(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM Students", null)
    }
}
