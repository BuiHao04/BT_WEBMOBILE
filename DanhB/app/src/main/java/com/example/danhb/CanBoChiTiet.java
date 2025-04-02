package com.example.danhb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.danhb.add.CanBo;
import com.example.danhb.database.DatabaseHelperCanBo;

public class CanBoChiTiet extends AppCompatActivity {
    private TextView txt_hoten, txt_chucvu, txt_dvct, txt_sdt, txt_email;
    private ImageView imv_canbo;
    private Button btn_edit, btn_delete;
    private String id; // ID của cán bộ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_canbo_chitiet);

        // Ánh xạ các View
        txt_hoten = findViewById(R.id.txt_hoten);
        txt_chucvu = findViewById(R.id.txt_chucvu);
        txt_dvct = findViewById(R.id.txt_dvct);
        txt_sdt = findViewById(R.id.txt_sdt);
        txt_email = findViewById(R.id.txt_email);
        imv_canbo = findViewById(R.id.imv_canbo);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        // Lấy ID từ Intent
        id = getIntent().getStringExtra("id");
        updateUI(); // Hiển thị thông tin cán bộ

        // Lắng nghe sự kiện gọi điện
        txt_sdt.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + txt_sdt.getText().toString()));
            startActivity(intent);
        });

        // Lắng nghe sự kiện gửi email
        txt_email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + txt_email.getText().toString()));
            startActivity(intent);
        });

        // Lắng nghe sự kiện xem thông tin đơn vị
        txt_dvct.setOnClickListener(view -> {
            Intent intent = new Intent(this, DonViActivity.class);
            startActivity(intent);
        });

        // Sự kiện nhấn nút chỉnh sửa
        btn_edit.setOnClickListener(view -> {
            Intent intent = new Intent(this, UpdateCanBo.class);
            intent.putExtra("id", id);
            startActivityForResult(intent, 1); // Yêu cầu cập nhật
        });

        // Sự kiện nhấn nút xóa
        btn_delete.setOnClickListener(view -> {
            Log.d("CanBoChiTiet", "Button delete clicked");

            if (id != null) {
                int idInt = Integer.parseInt(id);
                DatabaseHelperCanBo dbHelper = new DatabaseHelperCanBo(this);
                boolean success = dbHelper.deleteCanBo(idInt);

                if (success) {
                    Toast.makeText(this, "Cán bộ đã được xóa!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, CanBoActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình chi tiết sau khi xóa
                } else {
                    Toast.makeText(this, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không tìm thấy ID cán bộ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Khi quay lại từ màn hình chỉnh sửa, cập nhật dữ liệu mới
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            updateUI(); // Cập nhật lại giao diện sau khi chỉnh sửa
        }
    }

    private void updateUI() {
        if (id != null) {
            DatabaseHelperCanBo dbHelper = new DatabaseHelperCanBo(this);
            CanBo canBo = dbHelper.getCanBoById(id); // Lấy cán bộ theo ID

            if (canBo != null) {
                txt_hoten.setText(canBo.getName());
                txt_chucvu.setText(canBo.getChucvu());
                txt_sdt.setText(canBo.getSdt());
                txt_email.setText(canBo.getEmail());
                txt_dvct.setText(canBo.getDonvicongtac());

            }
        }
    }

}
