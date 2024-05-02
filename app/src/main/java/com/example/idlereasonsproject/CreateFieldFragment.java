package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.databinding.FragmentCreateFieldBinding;
import com.example.idlereasonsproject.iface.DrawerLocker;

public class CreateFieldFragment extends Fragment
{
    private FragmentCreateFieldBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        binding = FragmentCreateFieldBinding.inflate(inflater, container, false);
        ((DrawerLocker)getActivity()).setDrawerEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceBundle)
    {
        super.onViewCreated(view, savedInstanceBundle);

    }
}
