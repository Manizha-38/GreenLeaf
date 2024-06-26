package com.example.greenleaf.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greenleaf.R;
import com.example.greenleaf.adapter.CatalogAdapter;
import com.example.greenleaf.adapter.ProductSetAdapter;
import com.example.greenleaf.databinding.FragmentActionsBinding;
import com.example.greenleaf.databinding.FragmentMainBinding;
import com.example.greenleaf.datasource.DataSource;
import com.example.greenleaf.entity.Product;
import com.example.greenleaf.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class ActionsFragment extends Fragment {

    private FragmentActionsBinding binding;

    private DataSource dataSource;

    private CatalogAdapter adapter;
    private ProductSetAdapter adapterGrid;

    public ActionsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActionsBinding.inflate(getLayoutInflater());

        dataSource = new DataSource();
        adapter = new CatalogAdapter(new ArrayList<>());
        adapterGrid = new ProductSetAdapter(new ArrayList<>());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);

        binding.saleRecycler.setLayoutManager(layoutManager);
        binding.saleRecycler.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.saleRecycler.getContext(),
                layoutManager.getOrientation());
        Drawable mDivider = AppCompatResources.getDrawable(requireContext(),R.drawable.divider);
        dividerItemDecoration.setDrawable(mDivider);
        binding.saleRecycler.addItemDecoration(dividerItemDecoration);

        binding.recyclerSets.setLayoutManager(gridLayoutManager);
        binding.recyclerSets.setAdapter(adapterGrid);
        binding.recyclerSets.addItemDecoration(new SpaceItemDecoration(12));

        prepareData();
        prepareProductSets();

        return binding.getRoot();
    }

    private void prepareData(){
        MutableLiveData<List<Product>> list = dataSource.getAllProducts();

        list.observeForever( o-> {
            adapter = new CatalogAdapter(o);
            binding.saleRecycler.setAdapter(adapter);
        });
    }

    private void prepareProductSets(){
        MutableLiveData<List<Product>> list = dataSource.getProductSets();

        list.observeForever( o-> {
            adapterGrid = new ProductSetAdapter(o);
            binding.recyclerSets.setAdapter(adapterGrid);
        });
    }
}