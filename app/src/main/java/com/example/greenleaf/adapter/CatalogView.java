package com.example.greenleaf.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;

public class CatalogView extends RecyclerView.ViewHolder {

    ImageView image;
    TextView name;
    TextView price;
    TextView mass;
    Button button;

    TextView priceWithoutDiscount;

    ConstraintLayout llContainer;
    FrameLayout btnMinus;
    FrameLayout btnPlus;

    TextView tvProductQuantity;

    ImageView ivDelete;
    ImageView ivFavorite;


    public CatalogView(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        mass = itemView.findViewById(R.id.mass);
        button = itemView.findViewById(R.id.button);
        llContainer = itemView.findViewById(R.id.llContainer);
        priceWithoutDiscount = itemView.findViewById(R.id.priceWithoutDiscount);
        btnMinus = itemView.findViewById(R.id.btnMinus);
        btnPlus = itemView.findViewById(R.id.btnPlus);
        tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
        ivDelete = itemView.findViewById(R.id.ivDelete);
        ivFavorite = itemView.findViewById(R.id.ivFavorite);

    }
}