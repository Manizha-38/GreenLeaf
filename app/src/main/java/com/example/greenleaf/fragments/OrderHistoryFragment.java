package com.example.greenleaf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenleaf.adapter.OrderHistoryAdapter;
import com.example.greenleaf.databinding.FragmentOrderHistoryBinding;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.room.RoomHandler;

import java.util.List;

public class OrderHistoryFragment extends Fragment {

    private FragmentOrderHistoryBinding binding;


    public OrderHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderHistoryBinding.inflate(getLayoutInflater());
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        prepareData();
        return binding.getRoot();
    }

    private void prepareData(){
        Thread th1 = new Thread(() -> {
            List<Order> list = RoomHandler.getInstance(getContext(), "products").getAppDatabase().orderDao().getAll();
            binding.recycler.post(() ->{
                binding.recycler.setAdapter(new OrderHistoryAdapter(list));
            });
        });

        th1.start();

    }
}