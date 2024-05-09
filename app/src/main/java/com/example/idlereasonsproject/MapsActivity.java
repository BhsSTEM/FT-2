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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private List<LatLng> polygonPoints = new ArrayList<>();
    private LatLng endPoint;

    private Polygon polygon;

    private int markerDragged = -1;

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        PolygonOptions polygonOptions = new PolygonOptions();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Log.v("googleMaps", latLng.toString());

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true));

                if(polygonPoints.size() > 1)
                {
                    polygonPoints.add(polygonPoints.size()-1, latLng);
                    polygon.setPoints(polygonPoints);
                }
                else
                {
                    //set the first and last points to close the loop of the polygon
                    polygonPoints.add(latLng);
                    polygonPoints.add(latLng);
                    endPoint = latLng;
                    polygonOptions.addAll(polygonPoints);

                    polygon = mMap.addPolygon(polygonOptions);
                }

                Log.v("polygonPoints", polygonPoints.toString());
            }
        });

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker)
    {
        Log.v("onMarkerClick", marker.getPosition().toString());
        return false;
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker)
    {
        Log.v("onMarkerDrag", marker.getPosition().toString());
        LatLng newPos = marker.getPosition();
        Log.v("onMarkerDrag", String.valueOf(markerDragged));

        if(markerDragged == 0)
        {
            polygonPoints.set(markerDragged, newPos);
            polygonPoints.set(polygonPoints.size()-1, newPos);
        }

        polygon.setPoints(polygonPoints);
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker)
    {
        Log.v("onMarkerDragEnd", marker.getPosition().toString());
        markerDragged = -1;
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker)
    {
        LatLng pos = marker.getPosition();
        Log.v("onMarkerDragStart", pos.toString());
        Log.v("endpoint", endPoint.toString());
        Log.v("compare", String.valueOf(pos.equals(endPoint)));
        if(pos.equals(endPoint))
        {
            markerDragged = 0;
            return;
        }
    }
}