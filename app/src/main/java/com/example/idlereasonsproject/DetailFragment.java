package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.databinding.FragmentDetailBinding;


public class DetailFragment extends Fragment{

    private FragmentDetailBinding binding;
    MachineObject selectedShape;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    
        binding.activityDetailScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("selectedShapeName", selectedShape.getName());
                NavHostFragment.findNavController(DetailFragment.this)
                        .navigate(R.id.action_redirect_to_machine_reason_list);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}