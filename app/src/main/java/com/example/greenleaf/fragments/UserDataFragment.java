package com.example.greenleaf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.greenleaf.MainActivity;
import com.example.greenleaf.databinding.FragmentUserDataBinding;
import com.example.greenleaf.sharedpreferences.SharedPreferencesManager;
import com.example.greenleaf.utils.Replace;

public class UserDataFragment extends Fragment {

    private FragmentUserDataBinding binding;
    private SharedPreferencesManager sharedPreferencesManager;



    public UserDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserDataBinding.inflate(getLayoutInflater());

        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
        binding.name.setText(sharedPreferencesManager.getString("name",""));
        binding.phone.setText(sharedPreferencesManager.getString("phone",""));
        binding.address.setText(sharedPreferencesManager.getString("address",""));
        binding.email.setText(sharedPreferencesManager.getString("email",""));

        binding.confirmButton2.setOnClickListener(l->{
            checkFieldsAndSaveData();
        });


        return binding.getRoot();
    }

    private void checkFieldsAndSaveData(){
        if(binding.name.getText().toString().isEmpty()||binding.address.getText().toString().isEmpty()||binding.phone.getText().toString().isEmpty()||binding.email.getText().toString().isEmpty()){
            Toast.makeText(requireActivity(),"Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show();
        }else {
            sharedPreferencesManager.saveString("name", binding.name.getText().toString());
            sharedPreferencesManager.saveString("address", binding.address.getText().toString());
            sharedPreferencesManager.saveString("phone", binding.phone.getText().toString());
            sharedPreferencesManager.saveString("email", binding.email.getText().toString());

            Toast.makeText(requireActivity(),"Успешно сохранено", Toast.LENGTH_SHORT).show();

        }

    }
}