package com.example.danhb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.add.DonVi;

import java.util.List;

public class DonViAdapter extends RecyclerView.Adapter<DonViViewHolder> {
    private List<DonVi> donViList;
    private OnItemClickListener onItemClickListener;

    public DonViAdapter(List<DonVi> donViList) {
        this.donViList = donViList;
    }

    // Giao diện xử lý click item
    public interface OnItemClickListener {
        void onItemClick(DonVi donVi);  // Nhận DonVi thay vì CanBo
    }

    // Đăng ký sự kiện click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DonViViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donvi, parent, false);
        return new DonViViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonViViewHolder holder, int position) {
        DonVi donVi = donViList.get(position);
        holder.bind(donVi);

        // Lấy context từ itemView
        Context context = holder.itemView.getContext();

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(donVi);  // Truyền đối tượng DonVi

                // Chuyển đến DonViChiTiet và truyền dữ liệu DonVi
                Intent intent = new Intent(context, DonViChiTiet.class);
                intent.putExtra("id", donVi.getId());
                intent.putExtra("name", donVi.getName());  // Chuyển đối tượng DonVi vào Intent
                intent.putExtra("sdt", donVi.getSdt());
                intent.putExtra("address", donVi.getAddress());
                intent.putExtra("avatar", donVi.getAvatar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donViList.size();
    }

    // Cập nhật dữ liệu khi danh sách thay đổi
    public void updateData(List<DonVi> newDonViList) {
        this.donViList = newDonViList;
        notifyDataSetChanged();
    }
}
