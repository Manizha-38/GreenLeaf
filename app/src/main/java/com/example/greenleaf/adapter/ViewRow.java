package com.example.greenleaf.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;

public class ViewRow extends RecyclerView.ViewHolder {

    ImageView image;
    TextView name;
    TextView price;
    TextView priceWithoutDiscount;

    TextView mass;

    public ViewRow(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        mass = itemView.findViewById(R.id.mass);
        priceWithoutDiscount = itemView.findViewById(R.id.priceWithoutDiscount);
    }
}
