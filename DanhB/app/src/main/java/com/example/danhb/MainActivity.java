package com.example.danhb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.danhb.CanBoActivity;
import com.example.danhb.DonViActivity;

public class MainActivity extends AppCompatActivity {
    private Button btDonVi, btCBNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btDonVi = findViewById(R.id.unit_button);
        btCBNV = findViewById(R.id.employee_button);
        btDonVi.setOnClickListener(v -> {
            // Xử lý khi nút "Danh bạ đơn vị" được nhấn
            Intent intent = new Intent(MainActivity.this, DonViActivity.class);
            startActivity(intent);

        });
        btCBNV.setOnClickListener(v -> {
            // Xử lý khi nút "Danh bạ CBNV" được nhấn

            Intent intent = new Intent(MainActivity.this, CanBoActivity.class);
            startActivity(intent);
        });
    }


}