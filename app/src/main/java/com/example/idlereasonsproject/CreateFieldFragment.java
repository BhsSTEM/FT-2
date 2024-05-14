package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.FieldNode;
import com.example.idlereasonsproject.FBDatabase.FieldObject;
import com.example.idlereasonsproject.databinding.FragmentCreateFieldBinding;
import com.example.idlereasonsproject.iface.DrawerLocker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateFieldFragment extends Fragment implements OnMapReadyCallback
{
    private FragmentCreateFieldBinding binding;
    private FieldNode fieldNode;

    private EditText fieldNameInput;

    private MapView mapView;
    private GoogleMap map;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        binding = FragmentCreateFieldBinding.inflate(inflater, container, false);
        fieldNode = Database.fieldNode;
        fieldNameInput = binding.fieldNameTextInputLayout.getEditText();

        mapView = binding.createFieldMap;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceBundle)
    {
        super.onViewCreated(view, savedInstanceBundle);

        binding.createFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FieldObject field = new FieldObject(fieldNameInput.getText().toString());
                fieldNode.addField(new FieldObject(fieldNameInput.getText().toString()));

                NavHostFragment.findNavController(CreateFieldFragment.this)
                        .navigate(R.id.action_CreateField_to_FieldList); //later change to go to that new field details screen
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //test for now
        LatLng farm = new LatLng(41.722370, -90.595100);
        map.addMarker(new MarkerOptions().position(farm));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(farm,10));


        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onResume()
    {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
