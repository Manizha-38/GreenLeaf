package com.example.greenleaf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenleaf.R;
import com.example.greenleaf.adapter.DataAdapter;
import com.example.greenleaf.databinding.FragmentBasketBinding;
import com.example.greenleaf.databinding.FragmentFeedbackBinding;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.room.RoomHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;

    public FeedbackFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}