package com.example.greenleaf.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.greenleaf.R;
import com.example.greenleaf.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    private FusedLocationProviderClient mFusedLocationClient;

    Location mLastLocation;
    LocationRequest mLocationRequest;

    private GoogleMap mMap;

    private boolean isDistFound = false;


    public MapFragment() {

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getContext() != null) {
                    mLastLocation = location;
                    if(!isDistFound){
                        String[] arr = new String[4];
                        arr[0] = "Ферма 1:54.861987:55.812160";
                        arr[1] = "Ферма 2:54.816479:56.792642";
                        arr[2] = "Ферма 3:54.413066:55.962642";
                        arr[3] = "Ферма 4:54.598380:55.448423";

                        String name = "Ферма 0";
                        double minn = Double.MAX_VALUE;
                        for(String s: arr){
                            double lat = Double.parseDouble(s.split(":")[1]);
                            double lon = Double.parseDouble(s.split(":")[2]);

                            double dist = haversineDistance(
                                    mLastLocation.getLatitude(),
                                    mLastLocation.getLongitude(),
                                    lat,
                                    lon
                            );

                            if(dist < minn){
                                minn = dist;
                                name = s.split(":")[0];
                            }
                        }
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                        isDistFound=true;
                    }
                }
            }
        }
    };

    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(getLayoutInflater());

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        supportMapFragment.getMapAsync(this);
        return binding.getRoot();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        mMap.setMyLocationEnabled(true);

        LatLng farm1 = new LatLng(54.861987f, 55.812160f);
        mMap.addMarker(new MarkerOptions()
                .position(farm1)
                .title("Ферма 1"));

        LatLng farm2 = new LatLng(54.816479f, 56.792642f);
        mMap.addMarker(new MarkerOptions()
                .position(farm2)
                .title("Ферма 2"));

        LatLng farm3 = new LatLng(54.413066f, 55.962642f);
        mMap.addMarker(new MarkerOptions()
                .position(farm3)
                .title("Ферма 3"));

        LatLng farm4 = new LatLng(54.598380f, 55.448423f);
        mMap.addMarker(new MarkerOptions()
                .position(farm4)
                .title("Ферма 4"));



        mMap.setOnMarkerClickListener(marker -> {

            switch (marker.getTitle()){
                case "Ферма 1": {
                    Toast.makeText(getContext(), "Выбрана Ферма 1", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "Ферма 2": {
                    Toast.makeText(getContext(), "Выбрана Ферма 2", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "Ферма 3": {
                    Toast.makeText(getContext(), "Выбрана Ферма 3", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "Ферма 4": {
                    Toast.makeText(getContext(), "Выбрана Ферма 4", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            return false;
        });

    }

}