package com.example.smarthome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Locale;

/*
* Code lines 61-114 retrieved from: https://developer.android.com/training/location/retrieve-current
*/
public class LocationFragment extends Fragment {

    private Button locate;
    private TextView lat, lon;
    private FusedLocationProviderClient fusedLocationClient;

    /**
     * on location create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        //Initialise variables
        locate = view.findViewById(R.id.locateBtn);
        lat = view.findViewById(R.id.latTxt);
        lon = view.findViewById(R.id.lonTxt);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
                }
            }
        });

        return view;
    }

    /**
     * Ask for location request
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }  else {
                    Toast.makeText(getContext(),"Access Denied - Cannot access location", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    /**
     * extract the current loaction of device
     */
    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //Set latitude and longitude text
                            lat.setText(Double.toString(location.getLatitude()));
                            lon.setText(Double.toString(location.getLongitude()));
                        }
                    }
                });
    }
}
