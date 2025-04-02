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

import com.example.danhb.add.DonVi;
import com.example.danhb.database.DatabaseHelperDonVi;

import java.util.ArrayList;
import java.util.List;

public class DonViActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DonViAdapter donViAdapter;
    private List<DonVi> donViList;
    private DatabaseHelperDonVi dbHelper;
    private SearchView searchView;
    private LinearLayout layoutButtons;
    private Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_vi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rcv_donvi);
        searchView = findViewById(R.id.search_view);
        layoutButtons = findViewById(R.id.layout_buttons);
        btnThem = findViewById(R.id.btn_them);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donViList = new ArrayList<>();
        donViAdapter = new DonViAdapter(donViList);
        donViAdapter.setOnItemClickListener(donVi -> {
                    Intent intent = new Intent(DonViActivity.this, DonViChiTiet.class);
                    intent.putExtra("name", donVi.getName());
                    intent.putExtra("sdt", donVi.getSdt());
                    intent.putExtra("address", donVi.getAddress());
                    intent.putExtra("avatar", donVi.getAvatar());
                    startActivity(intent);
        });

        recyclerView.setAdapter(donViAdapter);
        dbHelper = new DatabaseHelperDonVi(this);
        btnThem.setOnClickListener(view -> {
            Intent intent = new Intent(DonViActivity.this,AddDonVi.class);
            startActivity(intent);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterDonVi(query);
                return true;
            }
            public boolean onQueryTextChange(String newText) {
                filterDonVi(newText);
                return true;
            }
        });
        loadData();
    }
    protected void onResume(){
        super.onResume();
        loadData();
    }
    private void loadData() {
        donViList.clear();
        donViList.addAll(dbHelper.getAllDonVi());
        donViAdapter.notifyDataSetChanged();

    }
    private void filterDonVi(String query) {
        List<DonVi> filteredList = new ArrayList<>();

        for (DonVi donVi : donViList) {
            if (donVi.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(donVi);
            }
        }
        donViAdapter.updateData(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();

        }
    }

}