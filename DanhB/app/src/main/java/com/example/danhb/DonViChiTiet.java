package com.example.danhb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.add.DonVi;
import com.example.danhb.database.DatabaseHelperDonVi;

public class DonViChiTiet extends AppCompatActivity {
    private TextView txt_name, txt_sdt, txt_diachi;
    private Button btn_edit, btn_delete;
    private ImageView imv_donvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_vi_chi_tiet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt_name = findViewById(R.id.txt_ten_donvi);
        txt_sdt = findViewById(R.id.txt_sdt_donvi);
        txt_diachi = findViewById(R.id.txt_diachi_donvi);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);
        imv_donvi = findViewById(R.id.imv_donvi);
        txt_sdt.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + txt_sdt.getText().toString()));
            startActivity(intent);
        });

        txt_diachi.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + txt_diachi.getText().toString()));
            startActivity(intent);
        });


        // Lấy thông tin đơn vị từ Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String sdt = intent.getStringExtra("sdt");
        String address = intent.getStringExtra("address");
        int avatar = intent.getIntExtra("avatar", R.drawable.user__1_);

        txt_name.setText(name);
        txt_sdt.setText(sdt);
        txt_diachi.setText(address);
        imv_donvi.setImageResource(avatar);



        btn_edit.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, UpdateDonVi.class);
            intent1.putExtra("id", id);
            intent1.putExtra("name", name);
            intent1.putExtra("sdt", sdt);
            intent1.putExtra("address", address);
            intent1.putExtra("avatar", avatar);
            startActivityForResult(intent1, 1); // Đặt requestCode là 1
        });

        btn_delete.setOnClickListener(view -> {

            String idToDelete = getIntent().getStringExtra("id");
            if (idToDelete != null) {
                int idInt = Integer.parseInt(idToDelete);
                DatabaseHelperDonVi dbHelper = new DatabaseHelperDonVi(this);
                boolean success = dbHelper.deleteDonVi(idInt);

                if (success) {
                    Toast.makeText(this, "Đơn vị đã được xóa!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                }
                Intent intent2 = new Intent(this, DonViActivity.class);
                startActivity(intent2);

            } else {
                Toast.makeText(this, "Không tìm thấy ID đơn vị!", Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Update views with new data
            if (data != null) {
                String updatedId = data.getStringExtra("id");
                String updatedName = data.getStringExtra("name");
                String updatedSdt = data.getStringExtra("sdt");
                String updatedAddress = data.getStringExtra("address");
                int updatedAvatar = data.getIntExtra("avatar", R.drawable.user__1_);

                txt_name.setText(updatedName);
                txt_sdt.setText(updatedSdt);
                txt_diachi.setText(updatedAddress);
                imv_donvi.setImageResource(updatedAvatar);
                Toast.makeText(this, "Cập nhật đơn vị thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }
}