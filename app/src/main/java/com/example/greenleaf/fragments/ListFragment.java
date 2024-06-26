package com.example.greenleaf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.greenleaf.adapter.CatalogAdapter;
import com.example.greenleaf.databinding.FragmentFruitsBinding;
import com.example.greenleaf.datasource.DataSource;
import com.example.greenleaf.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private FragmentFruitsBinding binding;
    private DataSource dataSource;

    private CatalogAdapter adapter;

    private String category;


    public ListFragment(String category) {
        this.category = category;
    }

    public void initViews(){
        binding.recycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);

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
        binding = FragmentFruitsBinding.inflate(getLayoutInflater());

        initViews();
        prepareData();


        return binding.getRoot();
    }


    private void prepareData(){
        MutableLiveData<List<Product>> list = dataSource.getProductsByCategory(category);

        list.observeForever( o-> {
            adapter = new CatalogAdapter(o);
            binding.recycler.setAdapter(adapter);

        });
    }
}