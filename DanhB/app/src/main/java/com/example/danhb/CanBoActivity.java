package com.example.danhb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.CanBoAdapter;
import com.example.danhb.add.CanBo;
import com.example.danhb.database.DatabaseHelperCanBo;

import java.util.ArrayList;
import java.util.List;

public class CanBoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnThem;
    private SearchView searchView;
    private LinearLayout layoutButtons;

    private CanBoAdapter canBoAdapter;
    private List<CanBo> canBoList;
    private DatabaseHelperCanBo dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_can_bo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ giao diện
        recyclerView = findViewById(R.id.rcv_canbo);
        btnThem = findViewById(R.id.btn_them);
        searchView = findViewById(R.id.search_view);
        layoutButtons = findViewById(R.id.layout_buttons);

        // Khởi tạo RecyclerView và Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        canBoList = new ArrayList<>();
        canBoAdapter = new CanBoAdapter(canBoList);
        canBoAdapter.setOnItemClickListener(canBo -> {
                    Intent intent = new Intent(CanBoActivity.this, CanBoChiTiet.class);
                    intent.putExtra("hoten", canBo.getName());
                    intent.putExtra("chucvu", canBo.getChucvu());
                    intent.putExtra("sdt", canBo.getSdt());
                    intent.putExtra("email", canBo.getEmail());
                    intent.putExtra("dvct", canBo.getDonvicongtac());
                    intent.putExtra("avatar", canBo.getAvatar());
                    startActivity(intent);
                    canBoAdapter.notifyDataSetChanged();

                });

        // Đặt Adapter cho RecyclerView
        recyclerView.setAdapter(canBoAdapter);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelperCanBo(this);

        // Nút thêm mới cán bộ
        btnThem.setOnClickListener(view -> {
            Intent intent = new Intent(CanBoActivity.this, Add_Canbo.class);
            startActivity(intent);
        });

        // Xử lý tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCanBo(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCanBo(newText);
                return true;
            }
        });

        // Tải dữ liệu khi khởi động
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Làm mới danh sách khi quay lại màn hình
        loadData();
    }

    // Hàm tải dữ liệu từ SQLite
    private void loadData() {
        canBoList.clear();
        canBoList.addAll(dbHelper.getAllCanBo());
        canBoAdapter.notifyDataSetChanged();
    }

    // Hàm lọc danh sách theo từ khóa
    private void filterCanBo(String query) {
        List<CanBo> filteredList = new ArrayList<>();
        for (CanBo canBo : canBoList) {
            if (canBo.getName().toLowerCase().contains(query.toLowerCase()) ||
                    canBo.getChucvu().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(canBo);
            }
        }
        canBoAdapter.updateData(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
        }
    }
}
