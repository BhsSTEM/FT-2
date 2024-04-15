package com.example.idlereasonsproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.databinding.FragmentMachineListBinding;

import java.util.ArrayList;
import java.util.Objects;


public class MachineListFragment extends Fragment {


    private FragmentMachineListBinding binding;
    private ListView listView;
    private ArrayList<MachineObject> machineList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentMachineListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = binding.machineObjectListView;

        // Retrieve machineList data from MainActivity and update the adapter
        machineList = MainActivity.machineList;
        setUpList();

/*
        binding.idleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIdleDialog();
            }
        });

*/

    }
    private void setUpList() {
        if (getContext() != null) {
            ObjectAdaptor adapter = new ObjectAdaptor(getContext(), 0, machineList);
            listView.setAdapter(adapter);
        }
    }
   // public void showIdleDialog(){
  //      Dialog dialog = new Dialog(requireContext());
    //    dialog.setContentView(R.layout.idle_instances_popup);
     //   dialog.show();
   // }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}