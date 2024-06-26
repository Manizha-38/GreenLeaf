package com.example.greenleaf.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greenleaf.R;
import com.example.greenleaf.databinding.FragmentProfileBinding;
import com.example.greenleaf.sharedpreferences.SharedPreferencesManager;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPreferencesManager sharedPreferencesManager;

    public ProfileFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        sharedPreferencesManager = new SharedPreferencesManager(getContext());

        String name = sharedPreferencesManager.getString("name", null);
        String phone = sharedPreferencesManager.getString("phone", null);
        binding.name.setText(name);
        binding.phone.setText(phone);

        binding.btnOrderHistory.setOnClickListener(l -> replace(new OrderHistoryFragment()));
        binding.llUserData.setOnClickListener(l -> replace(new UserDataFragment()));
//        binding.btnFeedback.setOnClickListener(l -> replace(new FeedbackFragment()));
        binding.btnFavorite.setOnClickListener(l -> replace(new FavoriteProductsFragment()));

        return binding.getRoot();
    }

    public void replace(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragments, fragment).commit();
    }
}