package com.example.idlereasonsproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.databinding.FragmentMachineListBinding;


public class MachineListFragment extends Fragment {


    private FragmentMachineListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentMachineListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.idleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIdleDialog();
            }
        });



    }
    public void showIdleDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.idle_instances_popup);
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}