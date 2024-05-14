package com.example.idlereasonsproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    //map to keep track of which marker goes with which polygon point
    private final Map<Marker, Integer> markerMap = new HashMap<>();

    //list to keep track of the polygon points
    private final List<LatLng> polygonPoints = new ArrayList<>();

    //marker that represents the start/end of the polygon
    private Marker endPoint;

    private Polygon polygon;

    private int markerDragged = -1;
    private boolean wasDragged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                    //int index = setNewPoint(latLng);
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

    public int setNewPoint(LatLng point)
    {
        if(polygonPoints.size() <= 3)
        {
            return 1;
        }

        int smallestIndex = 0;
        double smallestDist = findDistance(point, polygonPoints.get(0));

        int secondSmallestIndex = 1;
        double secondSmallestDist = findDistance(point, polygonPoints.get(1));

        for(int i=0; i<polygonPoints.size();i++)
        {
            double dist = findDistance(point, polygonPoints.get(i));

            if(dist < smallestDist)
            {
                smallestIndex = i;
                smallestDist = dist;
            }
            else if(dist < secondSmallestDist && dist > smallestDist && i != smallestIndex)
            {
                secondSmallestIndex = i;
                secondSmallestDist = dist;
            }
        }

        Log.v("index", smallestIndex + " " + smallestDist + "\n" + secondSmallestIndex + " " + secondSmallestDist +"\n" + polygonPoints.toString());
        return Math.min(smallestIndex, secondSmallestIndex);
    }

    public double findDistance(LatLng pnt1, LatLng pnt2)
    {
        double degsToRads = Math.PI/180;
        double earthRadius = 6371; //average radius in km

        //convert to radians
        double lon1 = pnt1.longitude * degsToRads;
        double lon2 = pnt2.longitude * degsToRads;
        double lat1 = pnt1.latitude * degsToRads;
        double lat2 = pnt2.latitude * degsToRads;

        //I split the equation to find distance using
        //lat/lon into 3 parts
        double a = Math.sin(lat1) * Math.sin(lat2);
        double b = Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);

        return Math.acos(a+b) * earthRadius;
    }
}