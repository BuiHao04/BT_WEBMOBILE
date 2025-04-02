package com.example.danhb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.add.CanBo;

import java.util.List;

public class CanBoAdapter extends RecyclerView.Adapter<CanBoViewHolder> {

    private List<CanBo> canBoList;
    private OnItemClickListener onItemClickListener;

    public CanBoAdapter(List<CanBo> canBoList) {
        this.canBoList = canBoList;
    }

    // Giao diện xử lý click item
    public interface OnItemClickListener {
        void onItemClick(CanBo canBo);
    }

    // Đăng ký sự kiện click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CanBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_canbo, parent, false);
        return new CanBoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanBoViewHolder holder, int position) {
        CanBo canBo = canBoList.get(position);
        holder.bind(canBo);

        Context context = holder.itemView.getContext();

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(canBo);
                // Chuyển đến CanBoChiTiet và truyền dữ liệu CanBo

                Intent intent = new Intent(context, CanBoChiTiet.class);
                intent.putExtra("id", canBo.getId());
                intent.putExtra("hoten", canBo.getName());
                intent.putExtra("chucvu", canBo.getChucvu());
                intent.putExtra("sdt", canBo.getSdt());
                intent.putExtra("email", canBo.getEmail());
                intent.putExtra("dvct", canBo.getDonvicongtac());
                intent.putExtra("avatar", canBo.getAvatar());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return canBoList.size();
    }

    // Cập nhật dữ liệu khi danh sách thay đổi
    public void updateData(List<CanBo> newCanBoList) {
        this.canBoList = newCanBoList;
        notifyDataSetChanged();
    }
}
