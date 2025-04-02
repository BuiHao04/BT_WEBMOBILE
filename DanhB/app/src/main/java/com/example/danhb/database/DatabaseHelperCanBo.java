package com.example.danhb.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.danhb.add.CanBo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperCanBo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CSDLCanBo.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_NAME = "CanBo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CHUCVU = "chucvu";
    private static final String COLUMN_SDT = "sdt";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DONVICONGTAC = "donvicongtac";
    private static final String COLUMN_AVATAR = "avatar";

    public DatabaseHelperCanBo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_CHUCVU + " TEXT, "
                + COLUMN_SDT + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_DONVICONGTAC + " TEXT, "
                + COLUMN_AVATAR + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm một cán bộ mới
    public boolean addCanBo(CanBo canBo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_CHUCVU + ", "
                    + COLUMN_SDT + ", " + COLUMN_EMAIL + ", " + COLUMN_DONVICONGTAC + ", " + COLUMN_AVATAR + ") VALUES (?, ?, ?, ?, ?, ?)";
            db.execSQL(sql, new Object[]{
                    canBo.getName(),
                    canBo.getChucvu(),
                    canBo.getSdt(),
                    canBo.getEmail(),
                    canBo.getDonvicongtac(),
                    canBo.getAvatar()
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // Cập nhật thông tin một cán bộ
    public boolean updateCanBo(CanBo canBo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + " = ?, " + COLUMN_CHUCVU + " = ?, "
                    + COLUMN_SDT + " = ?, " + COLUMN_EMAIL + " = ?, " + COLUMN_DONVICONGTAC + " = ?, "
                    + COLUMN_AVATAR + " = ? WHERE " + COLUMN_ID + " = ?";
            db.execSQL(sql, new Object[]{
                    canBo.getName(),
                    canBo.getChucvu(),
                    canBo.getSdt(),
                    canBo.getEmail(),
                    canBo.getDonvicongtac(),
                    canBo.getAvatar(),
                    canBo.getId()
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // Xóa một cán bộ
    public boolean deleteCanBo(int id) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
            db.execSQL(sql, new Object[]{id});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // Lấy danh sách tất cả cán bộ
    public List<CanBo> getAllCanBo() {
        List<CanBo> canBoList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String chucvu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHUCVU));
                    String sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                    String donvicongtac = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DONVICONGTAC));
                    int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));

                    canBoList.add(new CanBo(id, name, chucvu, sdt, email, donvicongtac, avatar));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return canBoList;
    }
    // Lấy cán bộ theo ID
    public CanBo getCanBoById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            // Truy vấn lấy dữ liệu cán bộ theo ID
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
            cursor = db.rawQuery(sql, new String[]{id});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy dữ liệu từ cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String chucvu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHUCVU));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String donvicongtac = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DONVICONGTAC));
                int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));

                // Tạo đối tượng CanBo và trả về
                return new CanBo(id, name, chucvu, sdt, email, donvicongtac, avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null; // Nếu không tìm thấy cán bộ
    }

}