package com.example.greenleaf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenleaf.R;
import com.example.greenleaf.adapter.DataAdapter;
import com.example.greenleaf.adapter.IDataAdapterOnClick;
import com.example.greenleaf.databinding.FragmentBasketBinding;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.room.RoomHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BasketFragment extends Fragment implements IDataAdapterOnClick {

    private FragmentBasketBinding binding;
    int totalPrice;

    public BasketFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initViews(){
        binding.recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setLayoutManager(layoutManager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(getLayoutInflater());
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Product> list = new ArrayList<>();
                Toast.makeText(requireActivity(),"Успешно оплачено", Toast.LENGTH_SHORT).show();
                Thread th1 = new Thread(() -> {
                    RoomHandler.getInstance(getContext(),"products").getAppDatabase().productDao().deleteAll();
                    binding.totalPrice.post(() ->{
                        binding.totalPrice.setText("Итоговая цена(без доставки): 0");
                        binding.recycler.setAdapter(new DataAdapter(list));
                    });
                });

                th1.start();

                Thread th2 = new Thread(() -> {
                    RoomHandler.getInstance(getContext(),"products").getAppDatabase().orderDao().insert(new Order(getDate(), String.valueOf(totalPrice)));
                });

                th2.start();

            }
        });

        initViews();
        prepareData();


        return binding.getRoot();
    }


    private void prepareData(){
        Thread th1 = new Thread(() -> {
            totalPrice = 0;
            List<Product> list = RoomHandler.getInstance(getContext(), "products").getAppDatabase().productDao().getAll();

            totalPrice = list.stream().mapToInt(Product::getTotalPrice).sum();

            binding.totalPrice.post(() ->{
               binding.totalPrice.setText("Итоговая цена(без доставки): "+totalPrice);
               DataAdapter dataAdapter = new DataAdapter(list);
               dataAdapter.setAdapterOnClick(this);
               binding.recycler.setAdapter(dataAdapter);
            });
        });

        th1.start();

    }
    private String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    public void onClick() {
        prepareData();
    }
}