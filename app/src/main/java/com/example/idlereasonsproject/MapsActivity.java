package com.example.idlereasonsproject;


import android.Manifest.permission;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.idlereasonsproject.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements ActivityCompat.OnRequestPermissionsResultCallback, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    //map to keep track of which marker goes with which polygon point
    private final Map<Marker, Integer> markerMap = new HashMap<>();

    //list to keep track of the polygon points
    private final List<LatLng> polygonPoints = new ArrayList<>();

    //marker that represents the start/end of the polygon
    private Marker endPoint;

    private Polygon polygon;

    private int markerDragged = -1;
    private boolean wasDragged = false;

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher, as an instance variable.

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        PolygonOptions polygonOptions = new PolygonOptions();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                //make new marker at click
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true));


                if(polygonPoints.size() > 1)
                {
                    //polygonPoints.size()-1
                    markerMap.put(marker, polygonPoints.size()-1);
                    polygonPoints.add(polygonPoints.size()-1, latLng);

                    polygon.setPoints(polygonPoints);
                }
                else
                {
                    //set the first and last points to close the loop of the polygon
                    polygonPoints.add(latLng);
                    polygonPoints.add(latLng);
                    markerMap.put(marker, 0);
                    endPoint = marker;

                    polygonOptions.addAll(polygonPoints);

                    polygon = mMap.addPolygon(polygonOptions);
                }

                Log.v("PolygonPoints", String.valueOf(polygonPoints.size()) + "\n" + polygonPoints.toString());

            }});


        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker)
    {
        Log.v("onMarkerClick", marker.getPosition().toString() +"\n" + markerMap.get(marker));
        return false;
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker)
    {
        wasDragged = true;
        LatLng newPos = marker.getPosition();
        //if the marker is the start/end point move both points
        if(markerDragged == 0)
        {
            polygonPoints.set(markerDragged, newPos);
            polygonPoints.set(polygonPoints.size()-1, newPos);
        }
        else
        {
            polygonPoints.set(markerDragged, newPos);
        }

        polygon.setPoints(polygonPoints);
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker)
    {
        if(!wasDragged)
        {
            marker.setPosition(polygonPoints.get(markerDragged));
        }

        //restart marker dragged once finished
        markerDragged = -1;
        wasDragged = false;
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker)
    {
        //get index of point from marker map
        markerDragged = markerMap.get(marker);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}