package com.example.greenleaf.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenleaf.R;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryView> {

    public List<Order> arrayList;

    public OrderHistoryAdapter(List<Order> arr){
        this.arrayList=arr;
    }

    @NonNull
    @Override
    public OrderHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_items,parent,false);
        return new OrderHistoryView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryView holder,int position){
        holder.tvDate.setText("Дата: "+ this.arrayList.get(position).getDate());
        holder.tvSum.setText("Cумма: "+this.arrayList.get(position).getSum()+" руб");
    }

    @Override
    public int getItemCount(){
        return this.arrayList.size();
    }
}

