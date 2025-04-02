package com.example.danhb;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.danhb.add.DonVi;
import com.example.danhb.database.DatabaseHelperDonVi;

public class AddDonVi extends AppCompatActivity {

    private EditText edtName, edtAddress, edtSDT;
    private Button btnSave;
    private TextView txtID;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_don_vi);

        // Xử lý giao diện hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các View
        txtID = findViewById(R.id.textView);
        edtName = findViewById(R.id.edit_name);
        edtAddress = findViewById(R.id.edit_address);
        edtSDT = findViewById(R.id.edit_sdt);
        btnSave = findViewById(R.id.btn_Add);

        // Xử lý nút Lưu
        btnSave.setOnClickListener(view -> {
            String id = txtID.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();

            // Kiểm tra đầu vào
            if (name.isEmpty() || address.isEmpty() || sdt.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sdt.length() != 10 || !sdt.matches("\\d+")) {
                Toast.makeText(context, "Số điện thoại phải là 10 chữ số hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng DonVi
            DonVi donVi = new DonVi(id, address, name, R.drawable.user__1_,sdt );

            // Lưu vào cơ sở dữ liệu
            DatabaseHelperDonVi dbHelper = new DatabaseHelperDonVi(context);
            boolean result = dbHelper.addDonVi(donVi);

            if (result) {
                Toast.makeText(context, "Thêm đơn vị thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity sau khi lưu
            } else {
                Toast.makeText(context, "Lỗi khi thêm đơn vị!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
