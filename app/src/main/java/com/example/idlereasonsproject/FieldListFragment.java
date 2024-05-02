package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        listView = binding.fieldListView;

        //setup list
        //setup buttons

        binding.addFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FieldListFragment.this)
                        .navigate(R.id.action_FieldList_to_CreateField);
            }
        });
    }
}