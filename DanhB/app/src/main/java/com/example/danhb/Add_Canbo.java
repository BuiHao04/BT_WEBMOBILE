package com.example.danhb;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.danhb.add.CanBo;
import com.example.danhb.database.DatabaseHelperCanBo;

public class Add_Canbo extends AppCompatActivity {
    private EditText edtHoten, edtChucvu, edtSdt, edtEmail, edtDvct, edtId;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_canbo);

        // Thiết lập insets cho giao diện
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view
        edtId = findViewById(R.id.edit_idAddCB);
        edtHoten = findViewById(R.id.edit_nameAddCB);
        edtChucvu = findViewById(R.id.edit_chucvuAddCB);
        edtSdt = findViewById(R.id.edit_sdtAddCB);
        edtEmail = findViewById(R.id.edit_emailAddCB);
        edtDvct = findViewById(R.id.edit_donvicongtacAddCB);
        btnSave = findViewById(R.id.btn_Luu);

        // Sự kiện khi nhấn nút Lưu
        btnSave.setOnClickListener(view -> {
            String id = edtId.getText().toString().trim();
            String hoten = edtHoten.getText().toString().trim();
            String chucvu = edtChucvu.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String dvct = edtDvct.getText().toString().trim();

            // Kiểm tra dữ liệu đầu vào
            if (id.isEmpty() || hoten.isEmpty() || chucvu.isEmpty() || sdt.isEmpty() || email.isEmpty() || dvct.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sdt.length() != 10 || !sdt.matches("\\d+")) {
                Toast.makeText(this, "Số điện thoại phải có 10 chữ số!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Khởi tạo đối tượng CanBo
            CanBo canBo = new CanBo(id, hoten, chucvu, sdt, email, dvct, R.drawable.user__1_);

            // Thêm dữ liệu vào SQLite
            DatabaseHelperCanBo dbHelper = new DatabaseHelperCanBo(this);
            boolean isInserted = dbHelper.addCanBo(canBo);

            // Xác nhận thêm thành công hoặc thất bại
            if (isInserted) {
                Toast.makeText(this, "Thêm cán bộ thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity
            } else {
                Toast.makeText(this, "Thêm cán bộ thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
