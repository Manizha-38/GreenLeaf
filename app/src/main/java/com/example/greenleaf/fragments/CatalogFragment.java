package com.example.greenleaf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.greenleaf.R;
import com.example.greenleaf.adapter.CatalogAdapter;
import com.example.greenleaf.databinding.FragmentCatalogBinding;
import com.example.greenleaf.datasource.DataSource;
import com.example.greenleaf.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class CatalogFragment extends Fragment {

    private FragmentCatalogBinding binding;
    private DataSource dataSource;

    private CatalogAdapter adapter;

    public CatalogFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatalogBinding.inflate(getLayoutInflater());
        binding.fruits.setOnClickListener(l -> replace(new ListFragment("fruits")));
        binding.vegetables.setOnClickListener(l -> replace(new ListFragment("vegetables")));
        binding.greens.setOnClickListener(l -> replace(new ListFragment("greens")));
        binding.berry.setOnClickListener(l -> replace(new ListFragment("berry")));
        binding.flowers.setOnClickListener(l -> replace(new ListFragment("flowers")));
        binding.fertilizers.setOnClickListener(l -> replace(new ListFragment("fertilizer")));
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

        binding.svSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.recycler.setVisibility(View.VISIBLE);
//                    binding.gridLayout.setVisibility(View.GONE);
                    binding.closeList.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recycler.setVisibility(View.GONE);
//                binding.gridLayout.setVisibility(View.VISIBLE);
                binding.closeList.setVisibility(View.GONE);

                binding.svSearch.clearFocus();
            }
        });



        dataSource = new DataSource();
        adapter = new CatalogAdapter(new ArrayList<>());
        adapter.isCatalogSearch = true;
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);

        prepareData();

        return binding.getRoot();
    }

    public void replace(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragments, fragment).commit();
    }

    private void prepareData(){
        MutableLiveData<List<Product>> list = dataSource.getAllProducts();

        list.observeForever( o-> {
            adapter = new CatalogAdapter(o);
            binding.recycler.setAdapter(adapter);
        });
    }
}