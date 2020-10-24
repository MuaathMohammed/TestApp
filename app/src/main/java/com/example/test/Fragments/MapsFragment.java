package com.example.test.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.Items.NewsDAO;
import com.example.test.Model.NewsItems;
import com.example.test.LocalData.SqlDataBase;
import com.example.test.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static com.example.test.Fragments.NewsFragment.newsItems;

public class MapsFragment extends Fragment {
NewsDAO newsDAO;
String newsTitle;
       double longitude,latitude;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            newsDAO=new SqlDataBase(getActivity());
            newsItems=newsDAO.getLocations();
            for(int i=0;i< newsItems.size();i++){
                newsTitle= newsItems.get(i).getNewsTitle();
                longitude=  newsItems.get(i).getLongitude();
                latitude= newsItems.get(i).getLatitude();
                LatLng latLng = new LatLng(longitude, latitude);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Title :"+newsTitle);
                googleMap.addMarker(markerOptions);
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitude,latitude),15.0f));
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
      View view =inflater.inflate(R.layout.fragment_maps, container, false);

permission();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchLocation();

    }
    public void permission(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
          fetchLocation();
    }
    }
    public void fetchLocation(){
                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(callback);
                    }
                }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                } else {
                    // Permission Denied
                  //  Toast.makeText( getActivity(),"You Have To Add Permission To Get The Exact News Location" , Toast.LENGTH_SHORT)
                       //     .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}