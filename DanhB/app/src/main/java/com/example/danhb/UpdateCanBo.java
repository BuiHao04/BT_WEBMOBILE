package com.example.danhb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.danhb.add.CanBo;
import com.example.danhb.database.DatabaseHelperCanBo;

public class UpdateCanBo extends AppCompatActivity {
    private TextView txt_id;
    private EditText txt_hoten;
    private EditText txt_chucvu;
    private EditText txt_dvct;
    private EditText txt_sdt;
    private EditText txt_email;
    private ImageView imv_canbo;
    private Button btn_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_canbo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Hiển thị nút quay lại
        }
        // Ánh xạ các View
        txt_id = findViewById(R.id.edit_idAddCB);
        txt_hoten = findViewById(R.id.edit_nameAddCB);
        txt_chucvu = findViewById(R.id.edit_chucvuAddCB);
        txt_dvct = findViewById(R.id.edit_donvicongtacAddCB);
        txt_sdt = findViewById(R.id.edit_sdtAddCB);
        txt_email = findViewById(R.id.edit_emailAddCB);
        btn_Save = findViewById(R.id.btn_Luu);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        txt_id.setText(intent.getStringExtra("id"));
        txt_hoten.setText(intent.getStringExtra("hoten"));
        txt_chucvu.setText(intent.getStringExtra("chucvu"));
        txt_dvct.setText(intent.getStringExtra("dvct"));
        txt_sdt.setText(intent.getStringExtra("sdt"));
        txt_email.setText(intent.getStringExtra("email"));


//        txt_id.setText(id);
//        txt_hoten.setText(hoten);
//        txt_chucvu.setText(chucvu);
//        txt_dvct.setText(dvct);
//        txt_sdt.setText(sdt);
//        txt_email.setText(email);

        // Lắng nghe sự kiện nhấn nút Lưu
        btn_Save.setOnClickListener(view -> {
            // Lấy thông tin từ các trường nhập liệu
            String updatedId = txt_id.getText().toString();
            String updatedHoten = txt_hoten.getText().toString();
            String updatedChucvu = txt_chucvu.getText().toString();
            String updatedDvct = txt_dvct.getText().toString();
            String updatedSdt = txt_sdt.getText().toString();
            String updatedEmail = txt_email.getText().toString();

            // Kiểm tra xem tất cả các trường có được điền đủ không
            if (updatedId.isEmpty() || updatedHoten.isEmpty() || updatedChucvu.isEmpty() || updatedDvct.isEmpty() || updatedSdt.isEmpty() || updatedEmail.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Tạo đối tượng CanBo và gọi hàm updateCanBo
                CanBo canBo = new CanBo(updatedId, updatedHoten, updatedChucvu, updatedDvct, updatedSdt, updatedEmail, R.drawable.user__1_);
                updateCanBo(canBo);
            }
        });
        // Xử lý các Insets cho thanh trạng thái và thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Hàm cập nhật thông tin cán bộ
    private void updateCanBo(CanBo canBo) {
        // Sử dụng DatabaseHelperCanBo để cập nhật thông tin
        DatabaseHelperCanBo dbHelper = new DatabaseHelperCanBo(this);
        boolean success = dbHelper.updateCanBo(canBo);

        // Hiển thị thông báo thành công hoặc thất bại
        if (success) {
            Toast.makeText(this, "Thông tin cán bộ đã được cập nhật!", Toast.LENGTH_SHORT).show();
            // Trả kết quả về CanBoChiTiet để cập nhật lại UI
            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", canBo.getId());  // Truyền lại ID nếu cần
            resultIntent.putExtra("hoten", canBo.getName());
            resultIntent.putExtra("chucvu", canBo.getChucvu());
            resultIntent.putExtra("dvct", canBo.getDonvicongtac());
            resultIntent.putExtra("sdt", canBo.getSdt());
            resultIntent.putExtra("email", canBo.getEmail());
            setResult(RESULT_OK, resultIntent);
        } else {
            Toast.makeText(this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
        }

        // Quay lại màn hình trước
        finish();
    }
}
