package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.databinding.FragmentFieldListBinding;

public class FieldListFragment extends Fragment
{

    private FragmentFieldListBinding binding;
    private ListView listView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFieldListBinding.inflate(inflater, container, false);
        listView = binding.fieldListView;

        //setup list here

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        listView = binding.fieldListView;

        //setup list
        //setup buttons
    }
}
