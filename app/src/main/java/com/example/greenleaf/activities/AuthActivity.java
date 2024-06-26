package com.example.greenleaf.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.greenleaf.MainActivity;
import com.example.greenleaf.R;
import com.example.greenleaf.databinding.ActivityAuthBinding;
import com.example.greenleaf.fragments.MainFragment;
import com.example.greenleaf.sharedpreferences.SharedPreferencesManager;
import com.example.greenleaf.utils.Replace;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(this);


        binding.confirmButton2.setOnClickListener(l->{
            checkFieldsAndSaveData();
        });
    }

    private void checkFieldsAndSaveData(){
        if(binding.name.getText().toString().isEmpty()||binding.phone.getText().toString().isEmpty()||binding.email.getText().toString().isEmpty()){
            Toast.makeText(this,"Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show();
        }else {
            sharedPreferencesManager.saveString("name", binding.name.getText().toString());
//            sharedPreferencesManager.saveString("address", binding.address.getText().toString());
            sharedPreferencesManager.saveString("phone", binding.phone.getText().toString());
            sharedPreferencesManager.saveString("email", binding.email.getText().toString());
            Replace.replaceActivity(this, new MainActivity(), true);
        }

    }
}