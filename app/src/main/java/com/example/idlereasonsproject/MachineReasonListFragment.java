package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.databinding.FragmentMachineReasonListBinding;

public class MachineReasonListFragment extends Fragment
{
    private FragmentMachineReasonListBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentMachineReasonListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //put binding check here
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
