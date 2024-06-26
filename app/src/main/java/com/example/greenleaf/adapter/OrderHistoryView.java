package com.example.greenleaf.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;

public class OrderHistoryView extends RecyclerView.ViewHolder {

    TextView tvDate;
    TextView tvSum;

    public OrderHistoryView(@NonNull View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvSum = itemView.findViewById(R.id.tvSum);
    }
}
