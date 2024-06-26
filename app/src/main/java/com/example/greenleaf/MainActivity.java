package com.example.greenleaf;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.view.MenuItem;

import com.example.greenleaf.activities.AuthActivity;
import com.example.greenleaf.databinding.ActivityMainBinding;
import com.example.greenleaf.fragments.ActionsFragment;
import com.example.greenleaf.fragments.BasketFragment;
import com.example.greenleaf.fragments.CatalogFragment;
import com.example.greenleaf.fragments.MapFragment;
import com.example.greenleaf.fragments.ProfileFragment;
import com.example.greenleaf.sharedpreferences.SharedPreferencesManager;
import com.example.greenleaf.utils.Replace;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferencesManager sharedPreferencesManager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem selectedItem;

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            Log.d("granted", "1");
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            Log.d("granted", "2");
                        } else {
                            // pass
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        sharedPreferencesManager = new SharedPreferencesManager(this);

        bottomNavigationView = binding.getterNavigation;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            selectFragment(item);
            return true;
        });



        selectedItem = bottomNavigationView.getMenu().getItem(0);

        selectFragment(selectedItem);

        if(sharedPreferencesManager.getString("name", null) == null){
            Replace.replaceActivity(this, new AuthActivity(), true);
        }else{
            selectFragment(bottomNavigationView.getMenu().getItem(0));
        }

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            int index = ((getSupportFragmentManager().getBackStackEntryCount()) -1);
            getSupportFragmentManager().popBackStack();
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            int stackId = backEntry.getId();
            //bottomNavigationView.getMenu().getItem(stackId).setChecked(true);
        }
    }


    public void selectFragment(MenuItem item) {

        Fragment fragment = null;
        item.setCheckable(true);
        FragmentManager fragmentManager = getSupportFragmentManager();


        if (item.getItemId() == R.id.actions) {
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            fragment = new ActionsFragment();
        } else if (item.getItemId() == R.id.catalog) {
            fragment = new CatalogFragment();
        } else if (item.getItemId() == R.id.map) {
            fragment = new MapFragment();
        } else if (item.getItemId() == R.id.basket) {
            fragment = new BasketFragment();
        } else if (item.getItemId() == R.id.profile) {
            fragment = new ProfileFragment();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragments, fragment).commit();

    }
}