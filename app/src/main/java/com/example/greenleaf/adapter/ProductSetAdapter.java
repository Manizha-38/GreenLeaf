package com.example.greenleaf.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.room.RoomHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductSetAdapter extends RecyclerView.Adapter<CatalogView> implements Filterable {

    public List<Product> arrayList;
    public List<Product> filterList;
    public Boolean isCatalogSearch = false;

    public ProductSetAdapter(List<Product> arr){
        this.arrayList=arr;
        this.filterList=arr;
    }

    @NonNull
    @Override
    public CatalogView onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_set_items,parent,false);
        return new CatalogView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogView holder,int position){
        holder.name.setText(this.arrayList.get(position).getName());
        holder.mass.setText(this.arrayList.get(position).getMass());
        holder.price.setText("Цена со скидкой "+String.valueOf(this.arrayList.get(position).getPrice()));
        holder.priceWithoutDiscount.setText("Цена "+String.valueOf(this.arrayList.get(position).getPriceWithoutDiscount()));
        holder.priceWithoutDiscount.setPaintFlags(holder.priceWithoutDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        if(isCatalogSearch){
            holder.llContainer.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        Picasso.get().load(this.arrayList.get(position).getImage()).into(holder.image);

        Thread th2 = new Thread(() ->{

            Product product = RoomHandler.getInstance(holder.button.getContext(), "products").getAppDatabase().productDao().getProductByName(
                    this.arrayList.get(position).getName()
            );
            if(product != null){
                holder.button.post(()->{
                    holder.button.setText("В корзине");
                    holder.button.setOnClickListener(l -> {
                        Thread th1 = new Thread(() ->{
                            RoomHandler.getInstance(l.getContext(), "products").getAppDatabase().productDao().delete(product);

                            holder.button.post(()->holder.button.setText("В корзину"));
                            holder.button.post(()->notifyItemChanged(holder.getAdapterPosition()));

                        });

                        th1.start();
                    });

                });

            }else{
                holder.button.post(()->{
                    holder.button.setText("В корзину");
                    holder.button.setOnClickListener(l -> {
                        Thread th1 = new Thread(() ->{

                            Product product2 = new Product(
                                    this.arrayList.get(position).getId(),
                                    this.arrayList.get(position).getImage(),
                                    this.arrayList.get(position).getName(),
                                    this.arrayList.get(position).getMass(),
                                    this.arrayList.get(position).getPrice(),
                                    this.arrayList.get(position).getPriceWithoutDiscount()
                            );

                            RoomHandler.getInstance(l.getContext(), "products").getAppDatabase().productDao().insertAll(product2);

                            holder.button.post(()->holder.button.setText("В корзине"));
                            holder.button.post(()->notifyItemChanged(holder.getAdapterPosition()));

                        });

                        th1.start();
                    });

                });
            }
        });

        th2.start();




    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    arrayList = filterList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product p : arrayList) {
                        if (p.getName().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(p);
                        }
                        arrayList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = arrayList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount(){
        return this.arrayList.size();
    }
}
