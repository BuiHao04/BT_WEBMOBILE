package com.example.danhb.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.danhb.add.CanBo;
import com.example.danhb.add.DonVi;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperDonVi extends SQLiteOpenHelper {

    // Thông tin cơ sở dữ liệu
    private static final String DATABASE_NAME = "CSDLDonVi.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_NAME = "DonVi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SDT = "sdt";
    private static final String COLUMN_AVATAR = "avatar";

    public DatabaseHelperDonVi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SDT + " TEXT, " +
                COLUMN_AVATAR + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm mới đơn vị
    public boolean addDonVi(DonVi donVi) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    " (" + COLUMN_ADDRESS + ", " + COLUMN_NAME + ", " + COLUMN_SDT + ", " + COLUMN_AVATAR + ") VALUES (?, ?, ?, ?)";
            db.execSQL(sql, new Object[]{donVi.getAddress(), donVi.getName(), donVi.getSdt(), donVi.getAvatar()});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin đơn vị
    public boolean updateDonVi(DonVi donVi) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String sql = "UPDATE " + TABLE_NAME +
                    " SET " + COLUMN_ADDRESS + " = ?, " + COLUMN_NAME + " = ?, " + COLUMN_SDT + " = ?, " + COLUMN_AVATAR + " = ? WHERE " + COLUMN_ID + " = ?";
            db.execSQL(sql, new Object[]{donVi.getAddress(), donVi.getName(), donVi.getSdt(), donVi.getAvatar(), donVi.getId()});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đơn vị
    public boolean deleteDonVi(int id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
            db.execSQL(sql, new Object[]{id});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public DonVi getCanBoById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            // Truy vấn lấy dữ liệu theo ID
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
            cursor = db.rawQuery(sql, new String[]{id});

            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT));
                int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));

                // Tạo đối tượng CanBo và trả về
                return new DonVi(id, name, address, avatar, sdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null;
    }

    // Lấy danh sách tất cả các đơn vị
    public List<DonVi> getAllDonVi() {
        List<DonVi> donViList = new ArrayList<>();
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {

            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT));
                    int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));

                    DonVi donVi = new DonVi(id, address, name, avatar, sdt);
                    donViList.add(donVi);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donViList;
    }
}
