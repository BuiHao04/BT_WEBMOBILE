package com.example.danhb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.danhb.add.DonVi;

public class DonViViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgAvatar;

    private TextView txtName;

    public DonViViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txt_tenDonvi);
        imgAvatar = itemView.findViewById(R.id.imv_donvi);
    }

    public void bind(DonVi donVi) {
        txtName.setText(donVi.getName());
        imgAvatar.setImageResource(donVi.getAvatar());
    }
}
