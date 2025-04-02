package com.example.danhb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.danhb.add.DonVi;
import com.example.danhb.database.DatabaseHelperDonVi;

public class UpdateDonVi extends AppCompatActivity {
    private EditText txt_id;
    private EditText txt_name;
    private EditText txt_address;
    private EditText txt_sdt;
    private Button btn_Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_don_vi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_id = findViewById(R.id.edit_ID);
        txt_name = findViewById(R.id.edit_name);
        txt_address = findViewById(R.id.edit_address);
        txt_sdt = findViewById(R.id.edit_sdt);

        btn_Save = findViewById(R.id.btn_Save);

        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String address = intent.getStringExtra("address");
            String sdt = intent.getStringExtra("sdt");

            // Set values to EditText fields
            if (id != null && name != null && address != null && sdt != null) {
                txt_id.setText(id);
                txt_name.setText(name);
                txt_address.setText(address);
                txt_sdt.setText(sdt);
            }
        }
        btn_Save.setOnClickListener(view -> {
            String updatedId = txt_id.getText().toString();
            String updatedName = txt_name.getText().toString();
            String updatedAddress = txt_address.getText().toString();
            String updatedSdt = txt_sdt.getText().toString();

            // Check if all fields are filled
            if (updatedId.isEmpty() || updatedName.isEmpty() || updatedAddress.isEmpty() || updatedSdt.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                DonVi donVi = new DonVi(updatedId, updatedName, updatedAddress, R.drawable.user__1_, updatedSdt);
                updateDonVi(donVi);
            }
        });


    }

    private void updateDonVi(DonVi donVi) {
        DatabaseHelperDonVi dbHelper = new DatabaseHelperDonVi(this);
        boolean success = dbHelper.updateDonVi(donVi);
        if (success) {
            // Gửi kết quả về DonViChiTiet
            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", donVi.getId());
            resultIntent.putExtra("name", donVi.getName());
            resultIntent.putExtra("sdt", donVi.getSdt());
            resultIntent.putExtra("address", donVi.getAddress());
            resultIntent.putExtra("avatar", donVi.getAvatar());
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Cập nhật đơn vị thành công", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Cập nhật đơn vị thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();


    }
}