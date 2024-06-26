package com.example.greenleaf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.greenleaf.R;
import com.example.greenleaf.adapter.CatalogAdapter;
import com.example.greenleaf.adapter.DataAdapter;
import com.example.greenleaf.databinding.FragmentFavoriteProductsBinding;
import com.example.greenleaf.databinding.FragmentFruitsBinding;
import com.example.greenleaf.datasource.DataSource;
import com.example.greenleaf.entity.FavoriteProduct;
import com.example.greenleaf.entity.Order;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.room.RoomHandler;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProductsFragment extends Fragment {

    private FragmentFavoriteProductsBinding binding;
    private DataSource dataSource;

    private CatalogAdapter adapter;

    private String category;


    public FavoriteProductsFragment() {
        this.category = category;
    }

    public void initViews() {
        binding.recycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recycler.getContext(),
                layoutManager.getOrientation());
        binding.recycler.addItemDecoration(dividerItemDecoration);
        binding.recycler.setLayoutManager(layoutManager);

        binding.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                adapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                adapter.getFilter().filter(queryString);
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new DataSource();
        adapter = new CatalogAdapter(new ArrayList<>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteProductsBinding.inflate(getLayoutInflater());

        initViews();
        prepareData();


        return binding.getRoot();
    }


    private void prepareData() {
        Thread th1 = new Thread(() -> {
            List<Product> list = RoomHandler.getInstance(getContext(), "products").getAppDatabase().favoriteProductDao().getAll();

            //totalPrice = list.stream().mapToInt(Product::getTotalPrice).sum();

            binding.recycler.post(() -> {
                //binding.totalPrice.setText("Итоговая цена(без доставки): "+totalPrice);
                adapter = new CatalogAdapter(list);
                //dataAdapter.setAdapterOnClick(this);
                binding.recycler.setAdapter(adapter);
            });
        });

        th1.start();

    }
}