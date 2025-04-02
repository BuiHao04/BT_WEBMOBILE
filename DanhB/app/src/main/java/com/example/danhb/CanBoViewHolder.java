package com.example.danhb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.add.CanBo;

public class CanBoViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtChucvu, txtDvct;
    private ImageView imgAvatar;

    public CanBoViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txt_hoten);
        txtChucvu = itemView.findViewById(R.id.txt_chucvu);
        txtDvct = itemView.findViewById(R.id.txt_dvct);
        imgAvatar = itemView.findViewById(R.id.imv_canbo);
    }

    public void bind(CanBo canBo) {
        txtName.setText(canBo.getName());
        txtChucvu.setText(canBo.getChucvu());
        txtDvct.setText(canBo.getDonvicongtac());
        imgAvatar.setImageResource(canBo.getAvatar());
    }
}
