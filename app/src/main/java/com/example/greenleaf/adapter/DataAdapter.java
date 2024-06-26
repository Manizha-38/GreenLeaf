package com.example.greenleaf.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.room.ProductDao;
import com.example.greenleaf.room.RoomHandler;
import com.squareup.picasso.Picasso;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<CatalogView> {

    public List<Product> arrayList;
    public IDataAdapterOnClick adapterOnClick;
    public DataAdapter(List<Product> arr){
        this.arrayList = arr;
    }

    public void setAdapterOnClick(IDataAdapterOnClick adapterOnClick) {
        this.adapterOnClick = adapterOnClick;
    }

    @NonNull
    @Override
    public CatalogView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems, parent, false);
        return new CatalogView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogView holder, int position) {
        holder.name.setText(this.arrayList.get(position).getName());
        holder.mass.setText(this.arrayList.get(position).getMass());
        holder.price.setText("Цена со скидкой "+String.valueOf(this.arrayList.get(position).getPrice()));
        holder.priceWithoutDiscount.setText("Цена "+String.valueOf(this.arrayList.get(position).getPriceWithoutDiscount()));
        holder.priceWithoutDiscount.setPaintFlags(holder.priceWithoutDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th1 = new Thread(() -> {
                    Product product = RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().getProductByName(
                            arrayList.get(holder.getAdapterPosition()).getName()
                    );
                    RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().delete(product);
                    arrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    adapterOnClick.onClick();

                });
                th1.start();

            }

        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th1 = new Thread(() -> {
                    Product product = RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().getProductByName(
                            arrayList.get(holder.getAdapterPosition()).getName()
                    );
                    int quantity = product.getQuantity();
                    quantity++;
                    product.setQuantity(quantity);
                    RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().update(product);
                    notifyItemChanged(holder.getAdapterPosition());
                    adapterOnClick.onClick();

                });
                th1.start();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th1 = new Thread(() -> {
                    Product product = RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().getProductByName(
                            arrayList.get(holder.getAdapterPosition()).getName()
                    );
                    int quantity = product.getQuantity();
                    if(quantity!=1) {
                        quantity--;
                    }
                    product.setQuantity(quantity);
                    RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().update(product);
                    notifyItemChanged(holder.getAdapterPosition());
                    adapterOnClick.onClick();

                });
                th1.start();
            }
        });

        Thread th1 = new Thread(() -> {
            Product product = RoomHandler.getInstance(holder.itemView.getContext(), "products").getAppDatabase().productDao().getProductByName(
                    arrayList.get(holder.getAdapterPosition()).getName()
            );
            int quantity = product.getQuantity();
            holder.tvProductQuantity.post(() ->{
                holder.tvProductQuantity.setText(String.valueOf(quantity));

            });
        });
        th1.start();

        Picasso.get().load(this.arrayList.get(position).getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }
}

